(ns musicality.compositions.voltageController2024.lattice-poly-chord
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o]
            [musicality.react-presets :refer :all]))

(comment
  (rc/connect)
  (rc/disconnect)
  (rc/print-state))

(def state (atom {
                  :sp1 nil
                  :ids []
                  }))

(comment

  (rc/organ- 0)
  (rc/organ+
    0
    (merge (:organ0 organ-presets)
           {
            :spawnFromCam (rc/transform :pos [0 0 2] :sca [0.5 0.5 0.5])

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

(def bw0 -1)
(def bw1 -1)
(def bw2 -1)
(def bw3 -1)

(comment

  (def sp1 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 -0.5 1] :sca [0.5 0.5 0.5])
                        :isLocked false
                        :isParent true
                        :label "A"))


  (def pos0 [-0.5 1 1])
  (def pos1 [0.5 1 1])
  (def pos2 [-0.5 2 1])
  (def pos3 [0.5 2 1])


  ;(rc/beat-wheel- 2)



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
        :ratio 3/8
        :spawnFromSpawner (rc/transform :pos pos1)
        :spawnerId sp1
        )))

  (do
    (rc/beat-wheel- bw2)
    (def bw2
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 5/16
        :spawnFromSpawner (rc/transform :pos pos2)
        :spawnerId sp1
        )))

  (do
    (rc/beat-wheel- bw3)
    (def bw3
      (rc/beat-wheel+
        :beats 2
        :pulses 3
        :ratio 7/8
        :spawnFromSpawner (rc/transform :pos pos3)
        :spawnerId sp1
        )))


  (rc/tonnegg-clear)


  (defn lattice-poly-chord-1 []
    (rc/tonneggs- (:ids @state))
    (let [chord [1/1 4/3 16/9 75/64 32/27]
          a [_]
          b [_ 1 2 _ 2 1 1 _ 1 2]
          c [_ 0 0 _ _ 0 0]
          d [3 4 3 4 3 4]
          ]
      (swap! state assoc :ids
             (concat
               (->> a
                    (tonnegg-circ-pattern
                      :organ0
                      sp1
                      pos0
                      (count a)
                      (fn [v] (nth chord v))
                      ))

               (->> b
                    (tonnegg-circ-pattern
                      :organ0
                      sp1
                      pos1
                      (count b)
                      (fn [v] (nth chord v))
                      ))

               (->> c
                    (tonnegg-circ-pattern
                      :organ0
                      sp1
                      pos2
                      (count c)
                      (fn [v] (nth chord v))
                      ))

               (->> d
                    (tonnegg-circ-pattern
                      :organ0
                      sp1
                      pos3
                      (count d)
                      (fn [v] (nth chord v))
                      ))))))

  (rc/tonnegg+ :fn #'lattice-poly-chord-1
               :instrument "/fn"
               :note-type :note-type/Function
               :val "lat"
               :spawnFromSpawner (rc/transform :pos [-0.5 0 0] :sca [0.1 0.1 0.1])
               :spawnerId sp1)

  (lattice-poly-chord-1)

  )





