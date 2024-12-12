(ns musicality.compositions.2024-12-08--voltage-controller-performance-prep
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o])
  (:use [overtone.osc :only (in-osc-bundle)])
  (:import (clojure.lang Numbers)))

(comment
  (rc/connect)
  (rc/disconnect)
  (rc/print-state))


(def state
  (atom
    {
     }))



(comment
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

  (in-osc-bundle (o/client) 0
                 (rc/tonneggs- somewhere))

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

(def organ-presets
  {
   :organ0 {:osc-addr                    "/organ"
            :osc-port-ambi-viz           8015
            :osc-port-audio-obj          7015
            :osc-port-directivity-shaper 6015}
   :organ1 {:osc-addr                    "/organ2"
            :osc-port-ambi-viz           8019
            :osc-port-audio-obj          7019
            :osc-port-directivity-shaper 6019}
   })


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

; TODO: pass beatwheel, use its position and spawner
(defn tonnegg-circ [preset spawner-id pos-center pos-on-circle val]
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
         :val              val
         :spawnFromSpawner (rc/transform
                             :pos [(+ x (nth pos-center 0))
                                   (+ y (nth pos-center 1))
                                   (nth pos-center 2)]
                             :sca [0.1 0.1 0.1])
         :spawnerId        spawner-id}))))

(defn tonnegg-circ-pattern [preset spawner-id pos-center divisions val-fn pattern]
  (->> pattern
       (map-indexed
         (fn [i v]
           (when v
             (tonnegg-circ
               (tonnegg-presets preset)
               spawner-id
               pos-center
               (/ i divisions)
               (val-fn v)
               ))))
       (doall)))


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



