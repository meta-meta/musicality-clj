(ns musicality.compositions.2024-06-19--musicality-vr-spawn-notes
  (:require [musicality.osc :as osc]
            [clojure.data.json :as json])
  (:import (clojure.lang Numbers)))

(osc/connect-local)



(defn organ+ [id osc-addr osc-port-ambi-viz osc-port-audio-obj osc-port-directivity-shaper diapason partials]
  (osc/send "/react/organ" (int id)
            (json/write-str {
                             :Diapason                   {:Val diapason :NoteType "Irrational"}
                             :OscAddress                 osc-addr
                             :OscPortAmbisonicVisualizer osc-port-ambi-viz
                             :OscPortAudioObject         osc-port-audio-obj
                             :OscPortDirectivityShaper   osc-port-directivity-shaper
                             :Partials                   partials
                             })))
(defn organ- [id] (osc/send "/react/organ" (int id)))
(comment
  (organ+ 0 "/organ" 8015 7015 6015 240
          (->> (range 16)
               (map (fn [i]
                      {:Interval  {:Val (+ 1 i) :NoteType "Irrational"}
                       :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                               2))
                       :Release   0
                       })
                    )))
  (organ- 0)

  (organ+ 1 "/organ2" 8019 7019 6019 240
          (->> (range 16)
               (map (fn [i]
                      {:Interval  {:Val (+ 1 (* 2 i)) :NoteType "Irrational"}
                       :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                               2))
                       :Release   0
                       })
                    )))
  (organ- 1)
  )


(defn to-ratio [maybe-ratio] (Numbers/toRatio maybe-ratio))

(defn midi-beat-clock-wheel+ [id maybe-ratio beats pulses]
  (let [ratio (Numbers/toRatio maybe-ratio)]
    (osc/send "/react/midiBeatClockWheel" (int id)
              (json/write-str {
                               :Beats      beats
                               :Pulses     pulses
                               :ClockRatio {
                                            :Val {
                                                  :Numerator   (numerator ratio)
                                                  :Denominator (denominator ratio)
                                                  }
                                            }
                               }))))

(defn midi-beat-clock-wheel- [id] (osc/send "/react/midiBeatClockWheel" (int id)))

(comment
  (midi-beat-clock-wheel+ 1 1/2 1 12)
  (midi-beat-clock-wheel- 1)
  )



(defn mallet+
  ([id]
   (mallet+ id [0 0 0] [0 0 0] [1.0 1.0 1.0]))
  ([id localPosition]
   (mallet+ id localPosition [0 0 0] [1.0 1.0 1.0]))
  ([id localPosition localRotation]
   (mallet+ id localPosition localRotation [1.0 1.0 1.0]))
  ([id localPosition localRotation localScale]
   (osc/send "/react/mallet" (int id)
             (json/write-str
               {:Transform
                {:localPosition
                 (let [[x y z] localPosition]
                   {:x x :y y :z z})
                 :localRotationEuler
                 (let [[x y z] localRotation]
                   {:x x :y y :z z})
                 :localScale
                 (let [[x y z] localScale]
                   {:x x :y y :z z})
                 }
                }))))

(defn mallet- [id] (osc/send "/react/mallet" (int id)))

;see: Musicality.NoteType
(def note-types #{:EDO
                  :Irrational
                  :JI
                  :RawFreq
                  :UnpitchedMidi})

(defn tonnegg+
  ([id note-type val] (tonnegg+ id note-type val [0 0 0]))
  ([id note-type val localPosition]
   (osc/send "/react/tonnegg" (int id)
             (json/write-str
               {:Note      (merge
                             {:NoteType (name note-type)}
                             (cond
                               (= note-type :JI)
                               (let [ratio (Numbers/toRatio val)]
                                 {:Val
                                  {:Numerator   (numerator ratio)
                                   :Denominator (denominator ratio)}})

                               (= note-type :EDO)
                               {:Val             val
                                :OctaveDivisions 12}))
                :Transform {:LocalPosition (let [[x y z] localPosition]
                                             {:x x :y y :z z})}}))))

(defn tonnegg- [id] (osc/send "/react/tonnegg" (int id)))

(comment

  (mallet+ 0 [0 0 0] [-30 -15 0])
  (mallet+ 1 [0.1 0 0] [-30 0 0])
  (mallet+ 2 [0.2 0 0] [-30 15 0] [2 2 2])
  (mallet- 0)
  (mallet- 1)
  (mallet- 2)

  (tonnegg- 0)
  (tonnegg+ 0 :JI 3/2)
  (tonnegg+ 0 :EDO 64)


  (tonnegg+ 0 :JI 3/2 [0.25 0 0])
  (tonnegg+ 0 :JI 4 [(* 0.25 (Math/sin 0)) (* 0.25 (Math/cos 0)) 0])


  (defn tonnegg-clear []
    (map (fn [i] (tonnegg- i)) (range 4)))

  (->> [[0 3/2] [1/4 5/4] [1/2 15/8] [3/4 1]]
       (map-indexed (fn [i [pos r]]
                      (let [theta (* Math/PI 2 pos)
                            trans [(* 0.25 (Math/sin theta)) (* 0.25 (Math/cos theta)) 0]]
                        (tonnegg+ i :JI r trans)))))

  (defn map-note [i [pos r]]
    (let [theta (* Math/PI 2 pos)
          trans [(* -0.25 (Math/sin theta)) (* 0.25 (Math/cos theta)) 0]]
      (tonnegg+ i :EDO (+ (* 12 7) r) trans)))

  (tonnegg-clear)

  (midi-beat-clock-wheel+ 1 1/2 1 12)
  (midi-beat-clock-wheel- 1)

  (defn map-notes [notes]
    (->> notes
         (map vector [0 1/4 2/4 3/4])
         (map-indexed map-note)
         ))

  (map-notes [9 7 6 7])
  (map-notes [7 5 4 5])
  (map-notes [5 4 3 4])
  (map-notes [4 2 1 2])

  (map-notes [9 7 6 7])
  (map-notes [10 9 8 9])
  (map-notes [12 10 9 10])
  (map-notes [9 7 5 4])

  ; TODO: Tonnegg as fn trigger to queue next measure
  )


(comment
  (def dir-beats (clojure.java.io/file "S:\\_Audio\\Samples From Mars"))
  (.getAbsolutePath (first (file-seq dir-beats)))

  (->> (file-seq dir-beats)
       (map #(.getAbsolutePath %))
       (filter #(clojure.string/ends-with? % "sfz"))))