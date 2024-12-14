(ns musicality.compositions.voltageController2024.rainbow
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
            :spawnFromCam (rc/transform :pos [-1 -1.5 3] :rot [20 -20 0])

            :diapason     240
            :partials     (->> (range 16)
                               (map (fn [i]
                                      {:Interval  {:Val      (+ 1 (* 2 i))
                                                   :NoteType "Irrational"}
                                       :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                               2))
                                       :Release   (if (> 3 i)
                                                    0
                                                    (* 0.3 (Math/pow (/ (- 15 i) 15)
                                                                     1)))})))}

           )))


(def bw0 -1)
(def bw1 -1)
(def posA [0 1 1])
(def posB [0 -0.25 1])

(comment

  (rc/spawner- sp1)
  (def sp1 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 0 1] :sca [0.5 0.5 0.5])
                        :isLocked false
                        :isParent true
                        :label "Rainbow"))


  (do
    (rc/beat-wheel- bw0)
    (def bw0
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/8
        :spawnFromSpawner (rc/transform :pos posA)
        :spawnerId sp1
        )))

  (do
    (rc/beat-wheel- bw1)
    (def bw1
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/4
        :spawnFromSpawner (rc/transform :pos posB)
        :spawnerId sp1
        )))

  (defn somewhere []
    (rc/tonneggs- (:ids @state))
    (swap! state assoc :ids
           (let [ns (->> [0 _ _ _ 7 _ _ _ 6 _ 4 5 6 _ 7 _
                          0 _ _ _ 5 _ _ _ 4 _ _ _ _ _ _ _
                          3 _ _ _ 3 _ _ _ 2 _ 0 1 2 _ 3 _
                          1 _ -1 0 1 _ 2 _ 0 _ _ _ _ _ _ _]
                         (identity)
                         #_(reverse)
                         #_(shuffle))
                 len (count ns)]
             (->> ns
                  (tonnegg-circ-pattern
                    :organ0
                    sp1
                    posA
                    len
                    (fn [v] (scale-deg->note :ptolemy-intense-diatonic (+ 0 v)))
                    )))))

  (somewhere)

  (rc/tonnegg+ :fn #'somewhere
               :instrument "/fn"
               :note-type :note-type/Function
               :val "smwhr"
               :spawnFromSpawner (rc/transform :pos [-0.5 0 0] :sca [0.1 0.1 0.1])
               :spawnerId sp2)

  (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))


  (rc/tonnegg-clear)

  )