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
            :spawnFromCam (rc/transform :pos [0 0 2] :rot [20 20 0] :sca [0.5 0.5 0.5])

            :diapason     120
            :partials     (->> (range 16)
                               (map (fn [i]
                                      {:Interval  {:Val      (+ 1 (* 2 i))
                                                   :NoteType "Irrational"}
                                       :Amplitude (* (Math/pow (/ (- 15 i)
                                                                  15)
                                                               1.1)
                                                     0.5)
                                       :Release   (if (> 0 i)
                                                    0
                                                    (* 0.7 (Math/pow (/ (- 15 i) 15)
                                                                     2)))})))}

           )))


(def bw0 -1)
(def bw1 -1)

(comment

  (rc/spawner- sp2)
  (def sp2 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 0 1] :sca [0.5 0.5 0.5])
                        :isLocked false
                        :isParent true
                        :label "B"))


  (def posA [0 1 1])
  (def posB [0 -0.2 1])

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

  (do
    (rc/beat-wheel- bw1)
    (def bw1
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/8
        :spawnFromSpawner (rc/transform :pos posB)
        :spawnerId sp2
        )))

  (defn harmonics []
    (rc/tonneggs- (:ids @state))
    (swap! state assoc :ids
           (let [ns (->> (range 1 (+ 2 (rand-int 20)) 1)
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

  (harmonics)

  (rc/tonnegg+ :fn #'harmonics
               :instrument "/fn"
               :note-type :note-type/Function
               :val "yo"
               :spawnFromSpawner (rc/transform :pos [-0.5 0 0] :sca [0.1 0.1 0.1])
               :spawnerId sp2)

  (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))


  (rc/tonnegg-clear)

  )