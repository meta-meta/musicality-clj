(ns musicality.compositions.2024-12-13--voltage-controller-performance-prep
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o]
            [musicality.react-presets :refer :all]))

(comment
  (rc/connect)
  (rc/disconnect)
  (rc/print-state))



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
                                      {:Interval  {:Val      (+ 1 (* 1 i))
                                                   :NoteType "Irrational"}
                                       :Amplitude (* (Math/pow (/ (- 15 i)
                                                                  15)
                                                               1.1))
                                       :Release   (if (> 5 i)
                                                    0
                                                    (* 0.5 (Math/pow (/ (- 15 i) 15)
                                                                     1)))})))}

           )))



(comment

  (def sp1 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 -0.5 1] :sca [0.5 0.5 0.5])
                        :isLocked false
                        :isParent true
                        :label "A"))


  (def pos0 [-0.5 1 1])
  (def pos1 [0.5 1 1])
  (def pos2 [-0.5 2 1])
  (def pos3 [0.5 2 1])



  (rc/beat-wheel- bw0)
  (def bw0
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 1
      :spawnFromSpawner (rc/transform :pos pos0)
      :spawnerId sp1
      ))

  (rc/beat-wheel- bw1)
  (def bw1
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 3/2
      :spawnFromSpawner (rc/transform :pos pos1)
      :spawnerId sp1
      ))

(rc/beat-wheel- bw2)
  (def bw2
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 5/4
      :spawnFromSpawner (rc/transform :pos pos2)
      :spawnerId sp1
      ))

(rc/beat-wheel- bw3)
  (def bw3
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 7/8
      :spawnFromSpawner (rc/transform :pos pos3)
      :spawnerId sp1
      ))


  (rc/tonnegg-clear)


  (defn lattice-poly-chord-1 []
    (let [chord [1/1 4/3 16/9 75/64 32/27]]
      (concat

        (->> [0 _ 0 _ 0 _]
             (tonnegg-circ-pattern
               :organ0
               sp1
               pos0
               6
               (fn [v] (nth chord v))
               ))

        (->> [1 _ 1 _ 1 _]
             (tonnegg-circ-pattern
               :organ0
               sp1
               pos1
               6
               (fn [v] (nth chord v))
               ))

        (->> [2 _ 2 _ 2 _]
             (tonnegg-circ-pattern
               :organ0
               sp1
               pos2
               6
               (fn [v] (nth chord v))
               ))

        (->> [3 _ 3 _ 3 _]
             (tonnegg-circ-pattern
               :organ0
               sp1
               pos3
               6
               (fn [v] (nth chord v))
               )))))

  (lattice-poly-chord-1)

  )





