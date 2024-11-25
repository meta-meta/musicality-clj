(ns musicality.compositions.2024-06-19--musicality-vr-spawn-notes
  (:require [musicality.osc :as osc]
            [musicality.react-cmp :as rc]))


(comment
  (osc/connect-local)
  (osc/disconnect)

  (rc/print-state)
  )

(comment
  (rc/organ+ 0
          :diapason 240
          :osc-addr "/organ"
          :osc-port-ambi-viz 8015
          :osc-port-audio-obj 7015
          :osc-port-directivity-shaper 6015
          :partials (->> (range 16)
                         (map (fn [i]
                                {:Interval  {:Val (+ 1 i) :NoteType "Irrational"}
                                 :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                         2))
                                 :Release   0
                                 })
                              )))

  (rc/tonnegg+ :note-type :JI
            :val 1/1
            :instrument "/organ"
            )

  (rc/mallet+ :spawnFromCam (transform :pos [0 0.2 1] :rot [-45 0 0]))


  (rc/organ- 0)


  (rc/organ+ 1
          :diapason 240
          :osc-addr "/organ2"
          :osc-port-ambi-viz 8019
          :osc-port-audio-obj 7019
          :osc-port-directivity-shaper 6019
          :partials (->> (range 16)
                         (map (fn [i]
                                {:Interval  {:Val (+ 1 (* 2 i)) :NoteType "Irrational"}
                                 :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                         4))
                                 :Release   0
                                 })
                              )))

  (rc/organ- 1)
  )





(comment

  (->> [[0 3/2] [1/4 5/4] [1/2 15/8] [3/4 1]]
       (map-indexed (fn [i [pos r]]
                      (let [theta (* Math/PI 2 pos)
                            trans [(* 0.25 (Math/sin theta)) (* 0.25 (Math/cos theta)) 0]]
                        (rc/tonnegg+ i :JI r trans)))))

  (defn map-note [i [pos r]]
    (let [theta (* Math/PI 2 pos)
          trans [(* -0.25 (Math/sin theta)) (* 0.25 (Math/cos theta)) 0]]
      (rc/tonnegg+ i :EDO (+ (* 12 7) r) trans)))

  (rc/tonnegg-clear)

  (rc/beat-wheel-send 1 1/2 1 12)
  (rc/beat-wheel+
    :beats 4
    :pulses 12
    :ratio 1/2
    )
  (rc/beat-wheel- 1)

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
  (map-notes [9 7 5 4]))

; TODO: Tonnegg as fn trigger to queue next measure



(comment
  (def dir-beats (clojure.java.io/file "S:\\_Audio\\Samples From Mars"))
  (.getAbsolutePath (first (file-seq dir-beats)))

  (->> (file-seq dir-beats)
       (map #(.getAbsolutePath %))
       (filter #(clojure.string/ends-with? % "sfz"))))



(comment
  (rc/organ+ 0
          :diapason 240
          :osc-addr "/organ"
          :osc-port-ambi-viz 8015
          :osc-port-audio-obj 7015
          :osc-port-directivity-shaper 6015
          :partials (->> (range 16)
                         (map (fn [i]
                                {:Interval  {:Val (+ 1 i) :NoteType "Irrational"}
                                 :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                         2))
                                 :Release   0
                                 })
                              )))

  (rc/organ+ 1
          :diapason 240
          :osc-addr "/organ2"
          :osc-port-ambi-viz 8019
          :osc-port-audio-obj 7019
          :osc-port-directivity-shaper 6019
          :partials (->> (range 16)
                         (map (fn [i]
                                {:Interval  {:Val (+ 1 (* 2 i)) :NoteType "Irrational"}
                                 :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                         4))
                                 :Release   0
                                 })
                              )))

  (rc/organ- 0)
  (rc/organ- 1)

  (rc/tonnegg+ :note-type :JI
            :val 7
            :instrument "/organ"
            )

  (rc/tonnegg+ :note-type :JI
            :val 1/1
            :instrument "/organ2"
            )

  (rc/mallet+ :spawnFromCam (transform :pos [0 0.2 1] :rot [-45 0 0]))

  (rc/beat-wheel+
    :beats 4
    :pulses 12
    :ratio 1/2
    )

  (rc/tonnegg+ :note-type :UnpitchedMidi
            :val 2
            :note-collection "CR78"
            :instrument "/cr78")

  (rc/tonnegg+ :note-type :UnpitchedMidi
            :val 1
            :note-collection "TR808"
            :instrument "/808")

  )









