(ns musicality.react-cmp
  (:require [musicality.osc :as osc]
            [clojure.data.json :as json])
  (:import (clojure.lang Numbers)))


(def components (atom {
                       :organs      {}
                       :beat-wheels {}
                       :mallets     {}
                       :tonneggs    {}
                       }))

(defn print-state [] (clojure.pprint/pprint @components))


(comment

  ; TODO: tonnegg fn trigger
  (defn send-fn
    "Sends a fn to be executed at beat"
    [instr beat fn-keyword fn]
    (swap! fns assoc fn-keyword fn)
    (send-beat instr beat :fn fn-keyword))

  (def ^:private fns "A map of fn keywords to definitions." (atom {}))

  (defn- handle-fn "Executes fn parsed from osc-msg."
    [osc-msg]
    (let [msg (first (:args osc-msg))
          fn-keyword (keyword msg)
          fns @fns]
      (if (contains? fns fn-keyword)
        ((fns fn-keyword))
        (println (str "couldn't find fn " fn-keyword)))
      nil))

  (osc/handle "/fn" #'handle-fn)
  )


(defn next-id [cmp-coll-key]
  (let [cmp-coll (cmp-coll-key @components)]
    (->> (range)
         (filter #(not (contains? cmp-coll %)))
         (first))))

(defn cmp+ [cmp-coll-key transforms state]
  (let [id (next-id cmp-coll-key)]
    (swap! components assoc-in [cmp-coll-key id]
           {:transforms transforms
            :state      state})
    id))

(defn cmp- [cmp-coll-key id]
  (swap! components #(update-in % [cmp-coll-key] dissoc id)))

(defn transform [& {:keys [pos rot sca]
                    :or   {pos [0 0 0]
                           rot [0 0 0]
                           sca [1 1 1]}}]
  {:localPosition      {:x (pos 0) :y (pos 1) :z (pos 2)}
   :localRotationEuler {:x (rot 0) :y (rot 1) :z (rot 2)}
   :localScale         {:x (sca 0) :y (sca 1) :z (sca 2)}})




(comment "Beat Wheel"
         (beat-wheel+
           :beats 4
           :pulses 12
           :ratio 1/2
           )

         (beat-wheel- 0)

         (beat-wheel+
           :beats 5
           :pulses 8
           :ratio 1/2
           )

         (beat-wheel+
           :beats 4
           :pulses 12
           :ratio 3/2
           )

         (beat-wheel- 1)
         )

(defn beat-wheel-send "Sends instructions to create or update a beat-wheel over osc"
  [id transforms state]
  (osc/send "/react/beatWheel" (int id)
            (json/write-str transforms)
            (json/write-str state)))

(defn beat-wheel+ "Adds a beat-wheel to state and sends over osc"
  [& {:keys [
             beats
             pulses
             ratio
             ]}]
  (let [r (Numbers/toRatio ratio)
        transforms {}
        state {
               :Beats  beats
               :Pulses pulses
               :ClockRatio
               {
                :Val
                {
                 :Numerator   (numerator r)
                 :Denominator (denominator r)
                 }
                }
               }
        id (cmp+ :beat-wheels transforms state)]
    (beat-wheel-send id transforms state)
    id
    )
  )

(defn beat-wheel- "removes beat-wheel from state and sends over osc"
  [id]
  (cmp- :beat-wheels id)
  (osc/send "/react/beatWheel" (int id)))




(comment "Mallet"
         (mallet+ :spawnFromCam (transform :pos [0 0 1]))
         (mallet- 0)

         (mallet+ :spawnFromCam (transform :pos [0 0.2 1] :rot [-45 0 0]))
         (mallet- 1)

         (mallet+ :localTransform (transform :sca [2 2 2]))
         (mallet- 2)
         )

(defn mallet-send "Sends instructions to create or update a mallet over osc"
  [id transforms state]
  (osc/send "/react/mallet" (int id)
            (json/write-str transforms)
            (json/write-str state)))

(defn mallet+
  [& {:keys [:localTransform :spawnFromCam]}]
  (let [transforms {:localTransform localTransform
                    :spawnFromCam   spawnFromCam
                    }
        state {}
        id (cmp+ :mallets transforms state)
        ]
    (mallet-send id transforms state)
    ))

(defn mallet- [id]
  (cmp- :mallets id)
  (osc/send "/react/mallet" (int id)))




(defn organ+ "Spawns an organ. Currently only osc-addr \"/organ1\" and \"/organ2\" are supported."
  [id & {:keys [
                diapason
                localTransform                              ; optional
                osc-addr
                osc-port-ambi-viz
                osc-port-audio-obj
                osc-port-directivity-shaper
                partials
                spawnFromCam                                ; optional
                ]
         :or   {partials
                (->> (range 16)
                     (map (fn [i]
                            {:Interval  {:Val      (+ 1 i)
                                         :NoteType "Irrational"}
                             :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                     2))
                             :Release   0
                             })
                          ))}}]
  (osc/send "/react/organ" (int id)
            (json/write-str
              {:LocalTransform localTransform
               :SpawnFromCam   spawnFromCam
               })
            (json/write-str {
                             :Diapason                   {:Val diapason :NoteType "Irrational"}
                             :OscAddress                 osc-addr
                             :OscPortAmbisonicVisualizer osc-port-ambi-viz
                             :OscPortAudioObject         osc-port-audio-obj
                             :OscPortDirectivityShaper   osc-port-directivity-shaper
                             :Partials                   partials
                             })))
(defn organ- [id] (osc/send "/react/organ" (int id)))





(comment "Tonnegg"

         (tonnegg+ :note-type :JI
                   :val 1/1
                   :instrument "/organ"
                   )
         (tonnegg+ :note-type :JI
                   :val 17/16
                   :instrument "/organ"
                   )

         (tonnegg+ :note-type :JI
                   :val 1/1
                   :instrument "/organ2"
                   )
         (tonnegg+ :note-type :JI
                   :val 3/2
                   :instrument "/organ2"
                   )

         (tonnegg+ :note-type :JI
                   :val 17/16
                   :instrument "/organ2"
                   )
         (tonnegg+ :note-type :EDO
                   :val 64
                   :instrument "/organ")

         (tonnegg+ :note-type :UnpitchedMidi
                   :val 2
                   :note-collection "CR78"
                   :instrument "/cr78")

         (tonnegg+ :note-type :UnpitchedMidi
                   :val 1
                   :note-collection "TR808"
                   :instrument "/808"))

;see: Musicality.NoteType
(def note-types #{:EDO
                  :Irrational
                  :JI
                  :RawFreq
                  :UnpitchedMidi})

(defn tonnegg-send ""
  [id transforms state]
  (osc/send "/react/tonnegg" (int id)
            (json/write-str transforms)
            (json/write-str state)))

(defn tonnegg+
  [& {:keys [
             :instrument
             :localTransform
             :spawnFromCam
             :note-collection
             :note-type
             :val
             ]}]
  (let [transforms {:localTransform localTransform
                    :spawnFromCam   spawnFromCam
                    }
        state {
               :Instrument instrument
               :Note
               (merge
                 {:NoteType (name note-type)}
                 (cond
                   (= note-type :JI)
                   (let [ratio (Numbers/toRatio val)]
                     {:Val
                      {:Numerator   (numerator ratio)
                       :Denominator (denominator ratio)}})

                   (= note-type :EDO)
                   {:Val             val
                    :OctaveDivisions 12}

                   (= note-type :UnpitchedMidi)
                   {:Val            val
                    :NoteCollection note-collection}
                   ))
               }
        id (cmp+ :tonneggs transforms state)
        ]
    (tonnegg-send id transforms state)))

(defn tonnegg- "removes beat-wheel from state and sends over osc"
  [id]
  (cmp- :tonneggs id)
  (osc/send "/react/tonnegg" (int id)))

(defn tonnegg-clear []
  (map (fn [i] (tonnegg- i)) (range 128)))




(defn handle-msg [cmp-coll-key osc-msg]
  (let [args (:args osc-msg)]
    (cond
      (= 1 (count args))                                    ; delete
      (let [id (first args)]
        (cond
          (= cmp-coll-key :beat-wheels)
          (beat-wheel- id)

          (= cmp-coll-key :mallets)
          (mallet- id)

          (= cmp-coll-key :tonneggs)
          (tonnegg- id)

          ))

      (= 2 (count args))                                    ;clone
      (let [[localTransform-json state-json] args
            transforms {:localTransform (json/read-str localTransform-json)}
            state (json/read-str state-json)
            id-new (cmp+ cmp-coll-key transforms state)
            ]
        (cond
          (= cmp-coll-key :beat-wheels)
          (beat-wheel-send id-new transforms state)

          (= cmp-coll-key :mallets)
          (mallet-send id-new transforms state)

          (= cmp-coll-key :tonneggs)
          (tonnegg-send id-new transforms state)

          )))

    )
  )

(osc/handle "/react/beatWheel" (partial handle-msg :beat-wheels))
(osc/handle "/react/mallet" (partial handle-msg :mallets))
(osc/handle "/react/tonnegg" (partial handle-msg :tonneggs))