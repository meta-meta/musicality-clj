(ns musicality.compositions.2024-12-08--voltage-controller-performance-prep
  (:require [musicality.react-cmp :as rc])
  (:import (clojure.lang Numbers)))

(comment
  (rc/connect)
  (rc/disconnect)
  (rc/print-state))

(comment
  (def sp1 (rc/spawner+ :spawnFromCam (rc/transform :pos [0 0 1])
                        :isLocked false
                        :isParent false
                        :label "Spawn Here"))

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

  (rc/beat-wheel- bw0)
  (def bw0
    (rc/beat-wheel+
      :beats 1
      :pulses 1
      :ratio 1/4
      :spawnFromSpawner (rc/transform :pos [0 1 1])
      :spawnerId sp1
      ))

  (rc/beat-wheel= bw0
                  :beats 1
                  :pulses 1
                  :ratio 5/16)


  )


(def tonnegg-presets
  {
   :organ0 {
            :note-type  :JI
            :val        1
            :instrument "/organ"}

   :organ1 {
            :note-type  :JI
            :val        1
            :instrument "/organ2"}

   :cr78   {:note-type       :UnpitchedMidi
            :val             1
            :note-collection "CR78"
            :instrument      "/cr78"}
   :tr808  {:note-type       :UnpitchedMidi
            :val             1
            :note-collection "TR808"
            :instrument      "/808"}})

(def scales {
             :archytas-enharmonic                  [1/1 28/27 16/15 4/3 3/2 14/9 8/5]
             :perret-tartini-pachymeres-enharmonic [1/1 21/20 16/15 4/3 3/2 63/40 8/5]
             :barbour-chromatic                    [1/1 64/63 8/7 4/3 3/2 32/21 12/7]
             :al-farabi-chromatic                  [1/1 16/15 8/7 4/3 3/2 8/5 12/7]
             :al-farabi-diatonic                   [1/1 8/7 64/49 4/3 3/2 12/7 96/49]
             :avicenna-malakon-diatonic            [1/1 10/9 8/7 4/3 3/2 5/3 12/7]
             :ptolemy-intense-diatonic             [1/1 9/8 5/4 4/3 3/2 5/3 15/8]})

(defn scale-deg->note [scale-key scale-degree]
  (let [scale (scale-key scales)
        n (nth scale (mod scale-degree (count scale)))
        mult (Numbers/toRatio                               ; (rationalize 1.0) => 1N
               (rationalize                                 ; (Numbers/toRatio 0.25) => 0/1
                 (Math/pow 2 (Math/floorDiv scale-degree
                                            (count scale)))))]
    (* n mult)))

(Numbers/toRatio (rationalize 0.25))

(def cr78-samples
  {:BongoH  0
   :BongoL  1
   :CH3     2
   :CH5     3
   :Conga5  4
   :Conga7  5
   :Kick    6
   :KickHi  7
   :OH3     8
   :OH5     9
   :OH7     10
   :Perc17  11
   :Perc27  12
   :Rim     13
   :Snare13 14
   :Snare14 15})

(def tr808-samples
  {:BDLo    0
   :BDLong  1
   :BDHi    2
   :CH      3
   :Clap    4
   :Claves  5
   :Cowbell 6
   :Cymbal  7
   :Maracas 8
   :OH      9
   :Rim     10
   :SnareLo 11
   :SnareHi 12
   :TomHi   13
   :TomLo   14
   :TomMid  15})

(def _ nil)
(def . nil)
(def x 1)

(defn tonnegg-circ [preset pos-center pos-on-circle val]
  (let [r (Numbers/toRatio pos-on-circle)
        a (* (numerator r)
             (/ (* 2 Math/PI)
                (denominator r)))
        x (* 0.5 (Math/sin a))
        y (* 0.5 (Math/cos a))
        ]
    (rc/tonnegg+
      (merge
        preset
        {
         :val          val
         :spawnFromCam (rc/transform
                         :pos [(+ x (nth pos-center 0))
                               (+ y (nth pos-center 1))
                               (nth pos-center 2)]
                         :sca [0.1 0.1 0.1])}))))

(defn tonnegg-circ-pattern [preset center-pos divisions val-fn pattern]
  (->> pattern
       (map-indexed
         (fn [i v]
           (when v
             (tonnegg-circ
               (tonnegg-presets preset)
               center-pos
               (/ i divisions)
               (val-fn v)
               ))))
       (doall)))


(comment

  (rc/organ- 0)
  (rc/organ+
    0
    :diapason 240
    :osc-addr "/organ"
    :osc-port-ambi-viz 8015
    :osc-port-audio-obj 7015
    :osc-port-directivity-shaper 6015
    :partials (->> (range 16)
                   (map (fn [i]
                          {:Interval  {:Val      (+ 1 (* 0.25 i))
                                       :NoteType "Irrational"}
                           :Amplitude (* (Math/pow (/ (- 15 i) 15)
                                                   1.1))
                           :Release   (* 0.25 (Math/pow (/ (- 15 i) 15)
                                                       1))})))



    :spawnFromCam (rc/transform :pos [-1 -1.5 3] :rot [20 -20 0])))




(comment

  (defn cos-amp [i phase progress]
    (* 1 (Math/cos (+ phase
                      progress))))
  (rc/organ- 1)
  (rc/organ+
    1
    :diapason 60
    :osc-addr "/organ2"
    :osc-port-ambi-viz 8019
    :osc-port-audio-obj 7019
    :osc-port-directivity-shaper 6019
    :partials (->> (range 16)
                   (map (fn [i]
                          {:Interval  {:Val      (+ 1 (* 1 i))
                                       :NoteType "Irrational"}
                           :Amplitude (cos-amp i
                                               (* 2/6 Math/PI)
                                               (* (* 2 Math/PI)
                                                  (/ i 15)))

                           :Release   0})))


    :spawnFromCam (rc/transform :pos [1 -1.5 3] :rot [20 20 0])))


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
             (fn [v] (cond (= 1 v) (:BDHi tr808-samples)
                           (= 2 v) (:SnareHi tr808-samples)))
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
             (fn [v] (:BDLo tr808-samples))
             ))
      (->> [x . x . . . . .
            x . x . . x . .]
           (tonnegg-circ-pattern
             :cr78
             pos0
             16
             (fn [v] (:Conga7 cr78-samples))
             ))
      (->> [. . 1 . . . . .
            1 . 2 2 2 . 2 1
            1 1 1 . 1 . . .
            1 . . . . . . .]
           (tonnegg-circ-pattern
             :cr78
             pos0
             32
             (fn [v] (cond (= 1 v) (:BongoL cr78-samples)
                           (= 2 v) (:BongoH cr78-samples)))
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
             (fn [v] (cond (= 1 v) (:BongoL cr78-samples)
                           (= 2 v) (:BongoH cr78-samples)))
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
             (fn [v] (cond (= 1 v) (:Conga5 cr78-samples)
                           (= 2 v) (:Conga7 cr78-samples)))
             ))
      ))

  )



