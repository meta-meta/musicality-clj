(ns musicality.react-cmp
  (:require
    [musicality.osc :as osc]
    [clojure.data.json :as json])
  (:use [overtone.osc :only (in-osc-bundle)])
  (:import (clojure.lang Numbers)))

(def initial-state
  {
   :beat-wheels {}
   :mallets     {}
   :organs      {}
   :spawners    {}
   :tonneggs    {}
   })

(def components (atom initial-state))

(defn print-state [] (clojure.pprint/pprint @components))

(defn clear-state []
  ; TODO: cmp- for each cmp in state
  (reset! components initial-state))

(defn- next-id [cmp-coll-key]
  (let [cmp-coll (cmp-coll-key @components)]
    (->> (range)
         (filter #(not (contains? cmp-coll %)))
         (first))))

(defn- cmp= "Updates a component in state."
  [cmp-coll-key id transforms state]
  (swap! components assoc-in [cmp-coll-key id]
         {:transforms transforms
          :state      state}))

(defn- cmp+ "Adds a component to state. Returns id of created component."
  [cmp-coll-key transforms state]
  (let [id (next-id cmp-coll-key)]
    (cmp= cmp-coll-key id transforms state)
    id))

(defn- cmp- "Removes a component from state."
  [cmp-coll-key id]
  (swap! components #(update-in % [cmp-coll-key] dissoc id)))

(defn- cmp-send-osc "Sends cmp state and transforms over osc."
  [addr id transforms state maybe-clone-source-id]
  (if maybe-clone-source-id
    (osc/send-osc addr (int id)
                  (json/write-str transforms)
                  (json/write-str state)
                  (int maybe-clone-source-id))
    (osc/send-osc addr (int id)
                  (json/write-str transforms)
                  (json/write-str state)))
  )

(defn transform [& {:keys [pos rot sca]
                    :or   {pos [0 0 0]
                           rot [0 0 0]
                           sca [1 1 1]}}]
  {:localPosition      {:x (pos 0) :y (pos 1) :z (pos 2)}
   :localRotationEuler {:x (rot 0) :y (rot 1) :z (rot 2)}
   :localScale         {:x (sca 0) :y (sca 1) :z (sca 2)}})




(comment "Beat Wheel"
         (def bw-id-1
           (beat-wheel+
             :beats 4
             :pulses 12
             :ratio 1/2
             ))

         (beat-wheel= bw-id-1
                      :beats 4
                      :pulses 5
                      :ratio 1/2)

         (beat-wheel- bw-id-1)
         )

(defn- beat-wheel-send "Sends instructions to create or update a beat-wheel over osc"
  [id transforms state maybe-clone-source-id]
  (cmp-send-osc "/react/beatWheel" id transforms state maybe-clone-source-id))


(defn- beat-wheel-keys->state
  [& {:keys [
             beats
             pulses
             ratio
             localTransform
             spawnFromCam
             spawnFromSpawner
             spawnerId
             ]}]
  (let [r (Numbers/toRatio ratio)
        transforms {:localTransform   localTransform
                    :spawnFromCam     spawnFromCam
                    :spawnFromSpawner spawnFromSpawner
                    :spawnerId        spawnerId
                    }
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
               }]
    [transforms state]))

(defn beat-wheel+ "Adds a beat-wheel to state and sends over osc. Returns id of the new beat-wheel."
  [& {:keys [
             beats
             pulses
             ratio
             localTransform
             spawnFromCam
             spawnFromSpawner
             spawnerId
             ]
      :as   opts}]
  (let [[transforms state] (beat-wheel-keys->state opts)
        id (cmp+ :beat-wheels transforms state)]
    (beat-wheel-send id transforms state nil)
    id
    )
  )

(defn beat-wheel= "Updates a beat-wheel and sends over osc. Returns id of the new beat-wheel."
  [id & {:keys [
                beats
                pulses
                ratio
                localTransform
                spawnFromCam
                spawnFromSpawner
                spawnerId
                ]
         :as   opts}]
  (let [[transforms state] (beat-wheel-keys->state opts)]
    (cmp= :beat-wheels id transforms state)
    (beat-wheel-send id transforms state nil))
  )

(defn beat-wheel- "Removes beat-wheel from state and sends over osc."
  [id]
  (cmp- :beat-wheels id)
  (osc/send-osc "/react/beatWheel" (int id)))




(comment "Mallet"
         (mallet+ :spawnFromCam (transform :pos [0 0 1]))
         (mallet- 0)

         (mallet+ :spawnFromCam (transform :pos [0 0.2 1] :rot [-45 0 0]))
         (mallet- 1)

         (mallet+ :localTransform (transform :sca [2 2 2]))
         (mallet- 2)
         )

(defn mallet-send "Sends instructions to create or update a mallet over osc"
  [id transforms state maybe-clone-source-id]
  (cmp-send-osc "/react/mallet" id transforms state maybe-clone-source-id))

(defn mallet+
  [& {:keys [
             localTransform
             spawnFromCam
             spawnFromSpawner
             spawnerId
             ]}]
  (let [transforms {:localTransform   localTransform
                    :spawnFromCam     spawnFromCam
                    :spawnFromSpawner spawnFromSpawner
                    :spawnerId        spawnerId
                    }
        state {}
        id (cmp+ :mallets transforms state)
        ]
    (mallet-send id transforms state nil)
    id
    ))

(defn mallet- [id]
  (cmp- :mallets id)
  (osc/send-osc "/react/mallet" (int id)))



; TODO add to state.organs
(defn organ+ "Creates or updates an organ. Currently only osc-addr \"/organ1\" and \"/organ2\" are supported."
  [id & {:keys [
                diapason
                localTransform                              ; optional
                osc-addr
                osc-port-ambi-viz
                osc-port-audio-obj
                osc-port-directivity-shaper
                partials
                spawnFromCam                                ; optional
                spawnFromSpawner                            ; optional
                spawnerId                                   ; optional
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
  (osc/send-osc "/react/organ" (int id)
                (json/write-str
                  {:LocalTransform   localTransform
                   :SpawnFromCam     spawnFromCam
                   :spawnFromSpawner spawnFromSpawner
                   :spawnerId        spawnerId
                   })
                (json/write-str {
                                 :Diapason                   {:Val diapason :NoteType "Irrational"}
                                 :OscAddress                 osc-addr
                                 :OscPortAmbisonicVisualizer osc-port-ambi-viz
                                 :OscPortAudioObject         osc-port-audio-obj
                                 :OscPortDirectivityShaper   osc-port-directivity-shaper
                                 :Partials                   partials
                                 })))
(defn organ- [id] (osc/send-osc "/react/organ" (int id)))




(comment "Spawner"
         (def spawner-id-1
           (spawner+
             :isLocked false
             :isParent false
             :label "Spawner One"
             ))

         (spawner= spawner-id-1
                   :isLocked true
                   :isParent false
                   :label "Spawner One (locked)")

         (spawner- spawner-id-1id-1)
         )

(defn- spawner-send "Sends instructions to create or update a spawner over osc"
  [id transforms state]
  (cmp-send-osc "/react/spawner" id transforms state nil))


(defn- spawner-keys->state
  [& {:keys [
             isLocked
             isParent
             label
             localTransform
             spawnFromCam
             spawnFromSpawner
             ]}]
  (let [transforms {:localTransform   localTransform
                    :spawnFromCam     spawnFromCam
                    :spawnFromSpawner spawnFromSpawner
                    }
        state {
               :isLocked isLocked
               :isParent isParent
               :label    label
               }]
    [transforms state]))

(defn spawner+ "Adds a spawner to state and sends over osc. Returns id of the new spawner."
  [& {:keys [
             isLocked
             isParent
             label
             localTransform
             spawnFromCam
             spawnFromSpawner
             ]
      :as   opts}]
  (let [[transforms state] (spawner-keys->state opts)
        id (cmp+ :spawners transforms state)]
    (spawner-send id transforms state)
    id
    )
  )

(defn spawner= "Updates a spawner and sends over osc. Returns id of the new spawner."
  [id & {:keys [
                isLocked
                isParent
                label
                localTransform
                spawnFromCam
                spawnFromSpawner
                ]
         :as   opts}]
  (let [[transforms state] (spawner-keys->state opts)]
    (cmp= :spawners id transforms state)
    (spawner-send id transforms state))
  )

(defn spawner- "Removes spawner from state and sends over osc."
  [id]
  (cmp- :spawners id)
  (osc/send-osc "/react/spawner" (int id)))





(comment "Tonnegg"

         (tonnegg+ :note-type :note-type/JI
                   :val 1/1
                   :instrument "/organ"
                   )
         (tonnegg+ :note-type :note-type/JI
                   :val 17/16
                   :instrument "/organ"
                   )

         (tonnegg+ :note-type :note-type/JI
                   :val 1/1
                   :instrument "/organ2"
                   )
         (tonnegg+ :note-type :note-type/JI
                   :val 3/2
                   :instrument "/organ2"
                   )

         (tonnegg+ :note-type :note-type/JI
                   :val 17/16
                   :instrument "/organ2"
                   )
         (tonnegg+ :note-type :note-type/EDO
                   :val 64
                   :instrument "/organ")

         (tonnegg+ :note-type :note-type/UnpitchedMidi
                   :val 2
                   :note-collection "CR78"
                   :instrument "/cr78")

         (tonnegg+ :note-type :note-type/UnpitchedMidi
                   :val 1
                   :note-collection "TR808"
                   :instrument "/808"))

;see: Musicality.NoteType
(def note-types #{:note-type/EDO
                  :Irrational
                  :note-type/JI
                  :RawFreq
                  :note-type/UnpitchedMidi})



(def ^:private fns "A map of fn keywords to definitions." (atom {}))

(defn add-fn
  "Adds fn to fns map."
  [fn-keyword fn]
  (swap! fns assoc fn-keyword fn)
  )

(defn- handle-fn "Executes fn parsed from osc-msg."
  [osc-msg]
  (let [msg (first (:args osc-msg))
        fn-keyword (keyword msg)
        fns @fns]
    (if (contains? fns fn-keyword)
      ((fns fn-keyword))
      (println (str "couldn't find fn " fn-keyword)))
    nil))


(defn- tonnegg-send ""
  [id transforms state fn maybe-clone-source-id]
  (when fn                                                  ; TODO: confirm note-type is Function
    (add-fn (keyword (get-in state [:Note :Val]))           ;TODO: allow namespaced keywords
            fn))
  (cmp-send-osc "/react/tonnegg" id transforms state maybe-clone-source-id))

(defn- tonnegg-keys->state
  [& {:keys [
             instrument
             localTransform
             note-collection
             note-type
             spawnFromCam
             spawnFromSpawner
             spawnerId
             val
             ]}]
  (let [transforms {:localTransform   localTransform
                    :spawnFromCam     spawnFromCam
                    :spawnFromSpawner spawnFromSpawner
                    :spawnerId        spawnerId
                    }
        state {
               :Instrument instrument
               :Note       (merge {:NoteType (name note-type)}
                                  (cond
                                    (= note-type :note-type/JI)
                                    (let [ratio (Numbers/toRatio val)]
                                      {:Val
                                       {:Numerator   (numerator ratio)
                                        :Denominator (denominator ratio)}})

                                    (= note-type :note-type/EDO)
                                    {:Val             val
                                     :OctaveDivisions 12}

                                    (= note-type :note-type/Function)
                                    {:Val val}

                                    (= note-type :note-type/UnpitchedMidi)
                                    {:Val            val
                                     :NoteCollection note-collection}
                                    ))
               }]
    [transforms state]))

(defn tonnegg+ "Adds tonnegg to state and sends over osc. Returns id of the created tonnegg."
  [& {:keys [
             fn
             instrument
             localTransform
             note-collection
             note-type
             spawnFromCam
             spawnFromSpawner
             spawnerId
             val
             ]
      :as   opts}]
  (let [[transforms state] (tonnegg-keys->state opts)
        id (cmp+ :tonneggs transforms state)
        ]
    (tonnegg-send id transforms state fn nil)
    id))

(defn tonnegg= "Updates tonnegg in state and sends over osc."
  [id & {:keys [
                fn
                instrument
                localTransform
                note-collection
                note-type
                spawnFromCam
                spawnFromSpawner
                spawnerId
                val
                ]
         :as   opts}]
  (let [[transforms state] (tonnegg-keys->state opts)]
    (cmp= :tonneggs id transforms state)
    (tonnegg-send id transforms state fn nil)
    id))

(defn tonnegg- "Removes beat-wheel from state and sends over osc."
  [id]
  (cmp- :tonneggs (int id))
  (osc/send-osc "/react/tonnegg" (int id)))

(defn tonnegg-clear []
  (in-osc-bundle (osc/client) 0
                 (doseq [id (range 128)] (tonnegg- id))))

(defn tonneggs- [ids]
  (in-osc-bundle (osc/client) 0
                 (doseq [x ids] (when x (tonnegg- x)))))




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

      (>= (count args) 2)                                   ;create or clone
      (let [[localTransform-json state-json maybe-clone-source-id] args
            transforms {:localTransform (json/read-str localTransform-json)}
            state (json/read-str state-json)
            id-new (cmp+ cmp-coll-key transforms state)
            ]
        (cond
          (= cmp-coll-key :beat-wheels)
          (beat-wheel-send id-new transforms state maybe-clone-source-id)

          (= cmp-coll-key :mallets)
          (mallet-send id-new transforms state maybe-clone-source-id)

          (= cmp-coll-key :tonneggs)
          (tonnegg-send id-new transforms state nil maybe-clone-source-id)

          )))

    )
  )

(defn connect []
  (osc/connect-local)
  (osc/handle "/fn" #'handle-fn)
  (osc/handle "/react/beatWheel" (partial handle-msg :beat-wheels))
  (osc/handle "/react/mallet" (partial handle-msg :mallets))
  (osc/handle "/react/tonnegg" (partial handle-msg :tonneggs)))

(defn disconnect []
  (osc/disconnect))
