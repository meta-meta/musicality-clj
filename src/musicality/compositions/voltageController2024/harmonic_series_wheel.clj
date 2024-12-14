(ns musicality.compositions.voltageController2024.harmonic-series-wheel
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

  (rc/organ- 1)
  (rc/organ+
    1
    (merge (:organ1 organ-presets)
           {
            :spawnFromCam (rc/transform :pos [1 -1.5 3] :rot [20 20 0])

            :diapason     120
            :partials     (->> (range 16)
                               (map (fn [i]
                                      {:Interval  {:Val      (+ 1 (* 2 i))
                                                   :NoteType "Irrational"}
                                       :Amplitude (* (Math/pow (/ (- 15 i)
                                                                  15)
                                                               1.1))
                                       :Release   (if (> 0 i)
                                                    0
                                                    (* 0.7 (Math/pow (/ (- 15 i) 15)
                                                                     2)))})))}

           )))


(comment

  (def sp2 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 -0.5 1] :sca [0.5 0.5 0.5])
                        :isLocked false
                        :isParent true
                        :label "B"))


  (def posA [0 1 1])

  (do
    (rc/beat-wheel- bw0)
    (def bw0
      (rc/beat-wheel+
        :beats 3
        :pulses 3
        :ratio 1/4
        :spawnFromSpawner (rc/transform :pos posA)
        :spawnerId sp2
        )))

  (defn harmonics []
    (rc/tonneggs- (:ids @state))
    (swap! state assoc :ids
           (let [ns (->> (range 1 1 1)
                         (identity)
                         #_(reverse)
                         (shuffle))
                 len (count ns)]
             (->> ns
               (tonnegg-circ-pattern
                 :organ1
                 sp2
                 posA
                 len
                 (fn [v] v)
                 )))))


  (rc/tonnegg+ :fn #'harmonics
               :instrument "/fn"
               :note-type :note-type/Function
               :val "yo"
               :spawnFromSpawner (rc/transform :pos [-0.5 0 0] :sca [0.1 0.1 0.1])
               :spawnerId sp2)

  (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))


  (rc/tonnegg-clear)  

  )