(ns musicality.compositions.voltageController2024.beats
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o]
            [musicality.react-presets :refer :all]))

(comment
  (rc/connect)
  (rc/disconnect)
  (rc/print-state))

(rc/tonnegg-clear)

(def state (atom {
                  :sp1 nil
                  :ids []
                  }))

(comment

  (def sp1 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 -0.5 1] :sca [0.5 0.5 0.5])
                        :isLocked false
                        :isParent true
                        :label "Beats"))

  (def pos0 [-0.6 1 1])
  (def pos1 [0.6 1 1])
  (def pos2 [0 2.1 1])


  (do
    (rc/beat-wheel- bw0)
    (def bw0
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/2
        :spawnFromSpawner (rc/transform :pos pos0)
        :spawnerId sp1
        )))

  (do
    (rc/beat-wheel- bw1)
    (def bw1
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/2
        :spawnFromSpawner (rc/transform :pos pos1)
        :spawnerId sp1
        )))

  (do
    (rc/beat-wheel- bw2)
    (def bw2
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/2
        :spawnFromSpawner (rc/transform :pos pos2)
        :spawnerId sp1
        )))


  (defn beats []
    (rc/tonneggs- (:ids @state))
    (let [chord [1/1 4/3 16/9 75/64 32/27]
          a [. x . x x x x . . x . . . . . .]
          b [x . x . . . . .
             x . x . . x . .]
          c [. . 1 . . . . .
             1 . 2 2 2 . 2 1
             1 1 1 . 1 . . .
             1 . . . . . . .]
          d [3 4 3 4 3 4]
          ]
      (swap! state assoc :ids
             (concat
               (->> a
                    (tonnegg-circ-pattern
                      :tr808
                      sp1
                      pos0
                      (count a)
                      (fn [v] (:tr808/BDLo tr808-samples))
                      ))

               (->> b
                    (tonnegg-circ-pattern
                      :cr78
                      sp1
                      pos1
                      (count b)
                      (fn [v] (:cr78/Conga7 cr78-samples))
                      ))

               (->> c
                    (tonnegg-circ-pattern
                      :cr78
                      sp1
                      pos2
                      (count c)
                      (fn [v] (cond (= 1 v) (:cr78/BongoL cr78-samples)
                                    (= 2 v) (:cr78/BongoH cr78-samples)))
                      ))
               ))))

  (rc/tonnegg+ :fn #'beats
               :instrument "/fn"
               :note-type :note-type/Function
               :val "beat-change"
               :spawnFromSpawner (rc/transform :pos [-0.5 0 0] :sca [0.1 0.1 0.1])
               :spawnerId sp1)

  )