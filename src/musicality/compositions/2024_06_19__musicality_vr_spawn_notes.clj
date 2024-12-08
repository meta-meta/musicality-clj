(ns musicality.compositions.2024-06-19--musicality-vr-spawn-notes
  (:require [musicality.react-cmp :as rc]))


(comment
  (rc/connect)
  (rc/disconnect)
  (rc/print-state)
  )


(comment "2024-12-07--performance-materials-prep"

         (rc/organ+ 0
                    :diapason 240
                    :osc-addr "/organ"
                    :osc-port-ambi-viz 8015
                    :osc-port-audio-obj 7015
                    :osc-port-directivity-shaper 6015
                    :partials (->> (range 16)
                                   (map (fn [i]
                                          {:Interval  {:Val (+ 1 (* 1 i)) :NoteType "Irrational"}
                                           :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                                   4))
                                           :Release   (* 0.5 (Math/pow (/ (- 15 i) 15)
                                                                   4))
                                           })
                                        )))


         (def organ1 {
                      :note-type  :JI
                      :val        1
                      :instrument "/organ"
                      })

         (def scales {
                      :archytas-enharmonic                  [1/1 28/27 16/15 4/3 3/2 14/9 8/5 2/1]
                      :perret-tartini-pachymeres-enharmonic [1/1 21/20 16/15 4/3 3/2 63/40 8/5 2/1]
                      :barbour-chromatic                    [1/1 64/63 8/7 4/3 3/2 32/21 12/7 2/1]
                      :al-farabi-chromatic                  [1/1 16/15 8/7 4/3 3/2 8/5 12/7 2/1]
                      :al-farabi-diatonic                   [1/1 8/7 64/49 4/3 3/2 12/7 96/49 2/1]
                      :avicenna-malakon-diatonic            [1/1 10/9 8/7 4/3 3/2 5/3 12/7 2/1]
                      :ptolemy-intense-diatonic             [1/1 9/8 5/4 4/3 3/2 5/3 15/8 2/1]
                      })

         (def t1 (rc/tonnegg+ :note-type :JI
                              :val 1
                              :instrument "/organ"
                              ))

         (rc/tonnegg= t1 (merge organ1 {:val 1}))


         (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))

         ; TODO: return transform of created cmp so it can be referenced to create new cmps relative to it while also being able to move the camera
         ; alternatively option to create and reference a container/group transform
         (def bw1
           (rc/beat-wheel+
             :beats 1
             :pulses 12
             :ratio 1/4
             :spawnFromCam (rc/transform :pos [-0.75 0 2])
             ))
         (rc/beat-wheel= bw1
                         :beats 1
                         :pulses 11
                         :ratio 5/8)
         (rc/beat-wheel- bw1)

         (def bw2
           (rc/beat-wheel+
             :beats 1
             :pulses 12
             :ratio 3/4
             :spawnFromCam (rc/transform :pos [0.75 0 2])
             ))
         (rc/beat-wheel= bw2
                         :beats 1
                         :pulses 11
                         :ratio 3/8)
         (rc/beat-wheel- bw2)




         (def tr808 {:note-type       :UnpitchedMidi
                     :val             1
                     :note-collection "TR808"
                     :instrument      "/808"})


         (defn tonnegg-circ [val x y opts]
           (rc/tonnegg+ (merge opts {
                                       :val          val
                                       :spawnFromCam (rc/transform :pos [x y 2] :sca [0.1 0.1 0.1])
                                       })))

         (def _ nil)


         (def )

         (def ts
           (->> [0 _ _ _ 7 _ _ _ 6 _ 4 5 6 _ 7 _
                 0 _ _ _ 5 _ _ _ 4 _ _ _ _ _ _ _
                 3 _ _ _ 3 _ _ _ 2 _ 0 1 2 _ 3 _
                 1 _ 6 0 1 _ 2 _ 0 _ _ _ _ _ _ _ ]
                (map-indexed (fn [i v] (when v
                                         (let [a (* i (/ (* 2 Math/PI) 64))]
                                           (tonnegg-circ (nth (:ptolemy-intense-diatonic scales) v)
                                                         (+ -0.75 (* 0.5 (Math/sin a)))
                                                         (* 0.5 (Math/cos a))
                                                         organ1))
                                         )))

                (doall)))


         (def ts
           (->> [2 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                 3 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                 2 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
                 6 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ]
                (map-indexed (fn [i v] (when v
                                         (let [a (* i (/ (* 2 Math/PI) 64))]
                                           (tonnegg-circ (nth (:ptolemy-intense-diatonic scales) v)
                                                         (+ -0.75 (* 0.5 (Math/sin a)))
                                                         (* 0.5 (Math/cos a))
                                                         organ1))
                                         )))

                (doall)))


         (def ts
           (->> [0 _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _
                 4 _ _ _ _ _ _ 6 _ _ _ _ _ _ _ _
                 0 _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _
                 5 _ _ _ _ _ _ 7 _ _ _ _ _ _ _ _ ]
                (map-indexed (fn [i v] (when v
                                         (let [a (* i (/ (* 2 Math/PI) 64))]
                                           (tonnegg-circ (nth (:ptolemy-intense-diatonic scales) v)
                                                         (+ 0.75 (* 0.5 (Math/sin a)))
                                                         (* 0.5 (Math/cos a))
                                                         organ1))
                                         )))

                (doall)))


         (def ts
           (->> [10 _ _ _ _ _ _ _
                 _ _ _ 4 _ _ _ _]
                (map-indexed (fn [i v] (when v
                                         (let [a (* i (/ (* 2 Math/PI) 16))]
                                           (tonnegg-circ v
                                                         (+ -0.75 (* 0.5 (Math/sin a)))
                                                         (* 0.5 (Math/cos a))
                                                         tr808))
                                         )))

                (doall)))

         (def ts
           (->> [8 8 8 8 _ _ _ _ 9 8 8 8 _ _ _ _
                 8 8 8 8 _ _ _ _ 9 8 8 8 _ _ _ _]
                (map-indexed (fn [i v] (when v
                                         (let [a (* i (/ (* 2 Math/PI) 32))]
                                           (tonnegg-circ v
                                                         (+ 0.75 (* 0.5 (Math/sin a)))
                                                         (* 0.5 (Math/cos a))
                                                         tr808))
                                         )))

                (doall)))

         ; TODO: in-osc-bundle?

         (doseq [x ts] (when x (rc/tonnegg- x)))

         (print ts)

         (rc/tonnegg-clear)

         (rc/print-state)
         (rc/tonnegg- 22)

         )



(comment "2024-12-01--drone-and-then?"
         (rc/organ+ 0
                    :diapason 60
                    :osc-addr "/organ"
                    :osc-port-ambi-viz 8015
                    :osc-port-audio-obj 7015
                    :osc-port-directivity-shaper 6015
                    :partials (->> (range 16)
                                   (map (fn [i]
                                          {:Interval  {:Val (+ 1 (* 1.5 i)) :NoteType "Irrational"}
                                           :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                                   2))
                                           :Release   0
                                           })
                                        )))


         (rc/tonnegg+ :note-type :JI
                      :val 1
                      :instrument "/organ"
                      )

         (rc/tonnegg+ :note-type :JI
                      :val 12/7
                      :instrument "/organ"
                      )

         (rc/tonnegg+ :note-type :JI
                      :val 8/7
                      :instrument "/organ"
                      )

         (defn cos-amp [i phase progress]
           (* 1 (Math/cos (+ phase
                             progress))))

         (rc/organ+ 1
                    :diapason 30
                    :osc-addr "/organ2"
                    :osc-port-ambi-viz 8019
                    :osc-port-audio-obj 7019
                    :osc-port-directivity-shaper 6019
                    :partials (->> (range 16)
                                   (map (fn [i]
                                          {:Interval  {:Val (+ 1 (* 1 i)) :NoteType "Irrational"}
                                           :Amplitude (cos-amp i
                                                               (* 2/6 Math/PI)
                                                               (* (* 2 Math/PI)
                                                                  (/ i 15))
                                                               )
                                           :Release   0
                                           })
                                        )))

         (rc/tonnegg+ :note-type :JI
                      :val 40/33
                      :instrument "/organ2"
                      )

         (rc/tonnegg+ :note-type :JI
                      :val 2/1
                      :instrument "/organ2"
                      )

         (rc/print-state)
         ; TODO: snapshot all state command.
         ; (vals (@rc/components :tonneggs)) make this queryable. currently different shape between (tonnegg+) and clone
         ; hotkey to put camera in follow mode
         ; hotkey for desktopDuplication follow cam


         (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))

         (rc/beat-wheel+
           :beats 4
           :pulses 4
           :ratio 1/2
           )

         )


(comment
  (rc/organ+ 0
             :diapason 60
             :osc-addr "/organ"
             :osc-port-ambi-viz 8015
             :osc-port-audio-obj 7015
             :osc-port-directivity-shaper 6015
             :partials (->> (range 16)
                            (map (fn [i]
                                   {:Interval  {:Val (+ 1 (* 1.5 i)) :NoteType "Irrational"}
                                    :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                            2))
                                    :Release   0
                                    })
                                 )))

  (rc/tonnegg+ :note-type :JI
               :val 1/1
               :instrument "/organ"
               )

  (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))


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
             :diapason 60
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
    :pulses 4
    :ratio 1/2
    )

  (rc/beat-wheel- 0)


  (rc/tonnegg+ :note-type :JI
               :val 1/1
               :instrument "/organ2"
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









