(ns musicality.compositions.2025-02-28-chords
  (:require [musicality.react-cmp :as rc]
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

(def s (atom {
              :chord-index 0
              :sp1         nil
              :tonnegg-ids []
              }))

(def posL [-0.6 1 0])
(def pos-r [0.6 1 0])


(comment "Organ0"

         (rc/organ- 0)

         (organ+ :diapason 120
                 :preset-kw :organ0
                 :spawnFromCam {:pos [0 0 2] :rot [3 0 0] :sca [0.5 0.5 0.5]}
                 :partials-intervals (range 1 17)
                 :partials-amplitudes (->> (range 16)
                                           (map #(* (Math/pow (/ (- 15 %)
                                                                 15)
                                                              2)
                                                    1)))

                 :partials-releases (->> (range 16)
                                         (map #(if (< 5 %)
                                                 0.17
                                                 (* 0.55 (Math/pow (/ (- 15 %) 15)
                                                                  2)))))
                 )
         )


(def bw0 -1)
(def bw1 -1)


(comment

  ; Spawner
  (rc/spawner- (:sp1 @s))
  (->> (rc/spawner+ :spawnFromCam (rc/transform :pos [0 -0.5 1] :sca [0.5 0.5 0.5])
                    :isLocked true
                    :isParent true
                    :label "Chords")
       (swap! s assoc :sp1))

  (do
    (rc/beat-wheel- bw0)
    (def bw0
      (rc/beat-wheel+
        :beats 1
        :pulses 1
        :ratio 1/2
        :spawnFromSpawner (rc/transform :pos posL)
        :spawnerId (:sp1 @s)
        )))

  (do
    (rc/beat-wheel- bw1)
    (def bw1
      (rc/beat-wheel+
        :beats 3
        :pulses 4
        :ratio 1/1
        :spawnFromSpawner (rc/transform :pos pos-r)
        :spawnerId (:sp1 @s)
        )))

  (defn next-chord []
    (rc/tonneggs- (:tonnegg-ids @s))
    (swap! s update-in [:chord-index] inc)
    (let [i (:chord-index @s)
          chords (:chords @s)
          ns (nth chords (mod i (count chords)))
          len (count ns)]
      (as-> ns v
            (tonnegg-circ-seq+
              v
              :pos-center pos-r
              :preset-kw :organ0
              :spawner-id (:sp1 @s)
              )
            (swap! s assoc :tonnegg-ids v))))

  (fn+ :fn #'next-chord
       :fn-name "next"
       :spawn-from-spawner {:pos (pos-on-circle posL 0)
                            :sca [0.1 0.1 0.1]}
       :spawner-id (:sp1 @s))


  (rc/tonnegg-clear)
  (next-chord)

  (swap! s assoc
         :chords
         [
          [1/1 5/4 3/2 27/25]
          [1/1 5/4 3/2 50/27 2/1]
          [4/3 5/3 2/1]
          [3/2 15/8 9/4 45/16 2/1]
          (->> [3/2 15/8 9/4 45/16 2/1]
               (map (fn [n] (* 8/9 n))))
          ])
  )