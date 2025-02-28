(ns musicality.compositions.2025-02-28-chords
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o]
            [musicality.osc-reaper :as rea]
            [musicality.react-presets :refer :all]))

(comment "Musicality OSC"
         (rc/connect)
         (rc/disconnect)
         (rc/clear-state)
         (rc/print-state)
         )

(comment "REAPER OSC"
         (rea/connect-local)
         (rea/disconnect)
         (rea/bpm-set 80)
         (rea/play)
         (rea/pause)
         (rea/click)
         )

(def state (atom {
                  :chord-index 0
                  :sp1 nil
                  :tonnegg-ids []
                  }))

(def posL [-0.75 1 0])
(def posR [0.75 1 0])


(comment

  (rc/organ- 0)
  (rc/organ+
    0
    (merge (:organ0 organ-presets)
           {
            :spawnFromCam (rc/transform :pos [0 0 2] :rot [3 0 0] :sca [0.5 0.5 0.5])

            :diapason     50
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

  (rc/spawner- (:sp1 @state))
  (->> (rc/spawner+ :spawnFromCam (rc/transform :pos [0 -0.5 1] :sca [0.5 0.5 0.5])
                    :isLocked false
                    :isParent true
                    :label "Chords")
       (swap! state assoc :sp1))




  (do
    (rc/beat-wheel- bw0)
    (def bw0
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/2
        :spawnFromSpawner (rc/transform :pos posL)
        :spawnerId (:sp1 @state)
        )))

  (do
    (rc/beat-wheel- bw1)
    (def bw1
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/1
        :spawnFromSpawner (rc/transform :pos posR)
        :spawnerId (:sp1 @state)
        )))

  (:chord-index @state)


  (swap! state assoc :chords [
                              [1/1 5/4 3/2]
                              [4/3 5/3 2/1]
                              [3/2 15/8 9/4]
                              ])

  (defn next-chord []
    (rc/tonneggs- (:tonnegg-ids @state))
    (swap! state update-in [:chord-index] inc)
    (let [i (:chord-index @state)
          chords (:chords @state)
          ns (nth chords (mod i (count chords)))
          len (count ns)]
      (->> ns
           (tonnegg-circ-pattern
             :organ0
             (:sp1 @state)
             posR
             len
             (fn [v] v)
             ))
      )
    )

  (rc/tonnegg+ :fn #'next-chord
               :instrument "/fn"
               :note-type :note-type/Function
               :val "next"
               :spawnFromSpawner (rc/transform :pos (pos-on-circle posL 0)
                                               :sca [0.1 0.1 0.1])
               :spawnerId (:sp1 @state))


  (defn gen []
    (rc/tonneggs- (:tonnegg-ids @state))
    (swap! state assoc :tonnegg-ids
           (let [ns (->> (range 1 (+ 1 (rand-int 15)) 1)
                         (identity)
                         #_(map (fn [i]
                                  (if (and (> 10 i) (even? i))
                                    _
                                    (* (if (odd? i) 5/22
                                                    5/16)
                                       i))

                                  ))
                         #_(reverse)
                         #_(shuffle)
                         )
                 len (count ns)]
             (->> ns
                  (tonnegg-circ-pattern
                    :organ0
                    (:sp1 @state)
                    posR
                    len
                    (fn [v] v)
                    )))))

  (swap! state assoc :chord-index 0)

  (gen)

  (rc/tonnegg+ :fn #'gen
               :instrument "/fn"
               :note-type :note-type/Function
               :val "gen"
               :spawnFromSpawner (rc/transform :pos (pos-on-circle posL 0)
                                               :sca [0.1 0.1 0.1])
               :spawnerId (:sp1 @state))

  (rc/mallet+ :spawnFromCam (rc/transform :pos [0 0.2 1] :rot [-45 0 0]))


  (rc/tonnegg-clear)

  )