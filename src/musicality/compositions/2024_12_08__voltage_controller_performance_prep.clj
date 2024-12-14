(ns musicality.compositions.2024-12-08--voltage-controller-performance-prep
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o]
            [musicality.react-presets :refer :all])

  )

(comment
  (rc/connect)
  (rc/disconnect)
  (rc/print-state))


(def state
  (atom
    {
     }))



(comment


  (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))

  (rc/tonnegg+ :fn #(println "yo world")
               :instrument "/fn"
               :note-type :note-type/Function
               :val "yoyo world"
               :spawnFromCam (rc/transform :pos [0 0 1] :sca [0.1 0.1 0.1]))


  (def sp1 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 -0.5 1] :sca [0.5 0.5 0.5])
                        :isLocked false
                        :isParent true
                        :label "A"))

  (rc/spawner- sp1)
  (rc/spawner= sp1
               :spawnFromCam (rc/transform :pos [0 0 1])
               :isLocked true
               :isParent false
               :label "Spawn Here(locked)")
  (rc/spawner= sp1
               :spawnFromCam (rc/transform :pos [0 0 1])
               :isLocked false
               :isParent true
               :label "Spawn Here")

  (def pos0 [0 1 1])


  (rc/beat-wheel- bw0)
  (def bw0
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 1/4
      :spawnFromSpawner (rc/transform :pos pos0)
      :spawnerId sp1
      ))

  (rc/beat-wheel= bw0
                  :beats 2
                  :pulses 3
                  :ratio 7/4)

  (rc/tonnegg-clear)

  (def riffs (atom {
                    :a []

                    }))

  (do
    (rc/tonneggs- (:a @riffs))
    (swap! riffs
           assoc
           :a
           (->> [0 _ _ _
                 _ _ _ _
                 _ _ _ _]
                (tonnegg-circ-pattern
                  :organ0
                  sp1
                  pos0
                  12
                  (fn [v] (scale-deg->note :ptolemy-intense-diatonic v))
                  ))))



  (do
    (rc/tonneggs- (:a @riffs))
    (swap! riffs
           assoc
           :a
           (->> [0 _ 1 _
                 2 _ _ _
                 0 _ _ _]
                (tonnegg-circ-pattern
                  :organ0
                  sp1
                  pos0
                  12
                  (fn [v] (scale-deg->note :ptolemy-intense-diatonic v))
                  ))))




  (def pos1 [0 2 1])

  (rc/beat-wheel- bw1)
  (def bw1
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 1/4
      :spawnFromSpawner (rc/transform :pos pos1)
      :spawnerId sp1
      ))

  (rc/beat-wheel= bw1
                  :beats 1
                  :pulses 1
                  :ratio 3/4)

  (do
    (rc/tonneggs- (:b @riffs))
    (swap! riffs
           assoc
           :b
           (->> [0 _ 1 _
                 _ _ _ _
                 11 _ _ _]
                (tonnegg-circ-pattern
                  :organ1
                  sp1
                  pos1
                  12
                  (fn [v] (scale-deg->note :ptolemy-intense-diatonic v))
                  ))))






  (defn riff-a []
    (rc/tonneggs- (:a @riffs))
    (swap! riffs
           assoc
           :a
           (->> [0 _ _ _ 7 _ _ _ 6 _ 4 5 6 _ 7 _
                 0 _ _ _ 5 _ _ _ 4 _ _ _ _ _ _ _
                 3 _ _ _ 3 _ _ _ 2 _ 0 1 2 _ 3 _
                 1 _ -1 0 1 _ 2 _ 0 _ _ _ _ _ _ _]
                (tonnegg-circ-pattern
                  :organ0
                  sp1
                  pos0
                  64
                  (fn [v] (scale-deg->note :ptolemy-intense-diatonic (- 3 v)))
                  )))

    )

  (defn riff-b []
    (rc/tonneggs- (:a @riffs))
    (swap! riffs
           assoc
           :a
           (->> [0 _ _ _ 7 _ _ _ 6 _ 4 5 6 _ 7 _
                 0 _ _ _ 5 _ _ _ 4 _ _ _ _ _ _ _
                 3 _ _ _ 3 _ _ _ 2 _ 0 1 2 _ 3 _
                 1 _ -1 0 1 _ 2 _ 0 _ _ _ _ _ _ _]
                (tonnegg-circ-pattern
                  :organ0
                  sp1
                  pos0
                  64
                  (fn [v] (scale-deg->note :ptolemy-intense-diatonic (+ 3 v)))
                  )))

    )

  (riff-a)
  (riff-b)

  (def somewhere
    (->> [0 _ _ _ 7 _ _ _ 6 _ 4 5 6 _ 7 _
          0 _ _ _ 5 _ _ _ 4 _ _ _ _ _ _ _
          3 _ _ _ 3 _ _ _ 2 _ 0 1 2 _ 3 _
          1 _ -1 0 1 _ 2 _ 0 _ _ _ _ _ _ _]
         (tonnegg-circ-pattern
           :organ0
           sp1
           pos0
           64
           (fn [v] (scale-deg->note :ptolemy-intense-diatonic (- 3 v)))
           )))

  )






(comment

  (rc/organ- 0)
  (rc/organ+
    0
    (merge (:organ0 organ-presets)
           {
            :spawnFromCam (rc/transform :pos [-1 -1.5 3] :rot [20 -20 0])

            :diapason     240
            :partials     (->> (range 16)
                               (map (fn [i]
                                      {:Interval  {:Val      (+ 1 (* 0.25 i))
                                                   :NoteType "Irrational"}
                                       :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                               2))
                                       :Release   (if (> 3 i)
                                                    0
                                                    (* 0.3 (Math/pow (/ (- 15 i) 15)
                                                                     1)))})))}

           )))




(comment

  (defn cos-amp [i phase progress]
    (* 1 (Math/cos (+ phase
                      progress))))
  (rc/organ- 1)
  (rc/organ+
    1
    (merge (:organ1 organ-presets)
           {
            :spawnFromCam (rc/transform :pos [1 -1.5 3] :rot [20 20 0])
            :diapason     60
            :partials     (->> (range 16)
                               (map (fn [i]
                                      {:Interval  {:Val      (+ 1 (* 2 i))
                                                   :NoteType "Irrational"}
                                       :Amplitude (* 0.5 (cos-amp i
                                                                  (* 2/6 Math/PI)
                                                                  (* (* 2 Math/PI)
                                                                     (/ i 15))))

                                       :Release   0})))})

    ))


(comment

  (def pos0 [0 1.4 2])

  (rc/beat-wheel- bw0)
  (def bw0
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 1/4
      :spawnFromCam (rc/transform :pos pos0)))

  (rc/beat-wheel= bw0
                  :beats 1
                  :pulses 1
                  :ratio 5/16)


  (rc/tonneggs- somewhere)
  (def somewhere
    (->> [0 _ _ _ 7 _ _ _ 6 _ 4 5 6 _ 7 _
          0 _ _ _ 5 _ _ _ 4 _ _ _ _ _ _ _
          3 _ _ _ 3 _ _ _ 2 _ 0 1 2 _ 3 _
          1 _ -1 0 1 _ 2 _ 0 _ _ _ _ _ _ _]
         (tonnegg-circ-pattern
           :organ0
           pos0
           64
           (fn [v] (scale-deg->note :ptolemy-intense-diatonic (- 3 v)))
           )))


  (rc/tonneggs- kick)
  (def kick
    (concat
      (->> [1 . . 1 . . . 1
            2 . . . . . . .]
           (tonnegg-circ-pattern
             :tr808
             pos0
             16
             (fn [v] (cond (= 1 v) (:tr808/BDHi tr808-samples)
                           (= 2 v) (:tr808/SnareHi tr808-samples)))
             ))
      ))



  (rc/tonneggs- man-on-corner-drums)
  (def man-on-corner-drums
    (concat
      (->> [. x . x x x x . . x . . . . . .]
           (tonnegg-circ-pattern
             :tr808
             pos0
             16
             (fn [v] (:tr808/BDLo tr808-samples))
             ))
      (->> [x . x . . . . .
            x . x . . x . .]
           (tonnegg-circ-pattern
             :cr78
             pos0
             16
             (fn [v] (:cr78/Conga7 cr78-samples))
             ))
      (->> [. . 1 . . . . .
            1 . 2 2 2 . 2 1
            1 1 1 . 1 . . .
            1 . . . . . . .]
           (tonnegg-circ-pattern
             :cr78
             pos0
             32
             (fn [v] (cond (= 1 v) (:cr78/BongoL cr78-samples)
                           (= 2 v) (:cr78/BongoH cr78-samples)))
             ))
      ))

  )

(comment
  (def pos1 [-0.75 0.5 2.2])

  (rc/beat-wheel- bw1)
  (def bw1
    (rc/beat-wheel+
      :beats 1
      :pulses 12
      :ratio 1/4
      :spawnFromCam (rc/transform :pos pos1)))

  (rc/beat-wheel= bw1
                  :beats 1
                  :pulses 11
                  :ratio 7/16)

  (rc/tonneggs- bongos)
  (def bongos
    (concat
      (->> [1 . 2 .
            1 1 . .
            1 . . 2]
           (tonnegg-circ-pattern
             :cr78
             pos1
             12
             (fn [v] (cond (= 1 v) (:cr78/BongoL cr78-samples)
                           (= 2 v) (:cr78/BongoH cr78-samples)))
             ))
      ))

  )

(comment
  (def pos2 [0.75 0.5 2.2])

  (rc/beat-wheel- bw2)
  (def bw2
    (rc/beat-wheel+
      :beats 1
      :pulses 12
      :ratio 1/4
      :spawnFromCam (rc/transform :pos pos2)))

  (rc/beat-wheel= bw2
                  :beats 1
                  :pulses 11
                  :ratio 3/8)


  (rc/tonneggs- conga)
  (def conga
    (concat
      (->> [1 . . . . .
            2 . . . . .]
           (tonnegg-circ-pattern
             :cr78
             pos2
             12
             (fn [v] (cond (= 1 v) (:cr78/Conga5 cr78-samples)
                           (= 2 v) (:cr78/Conga7 cr78-samples)))
             ))
      ))

  )



