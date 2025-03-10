(ns musicality.react-presets
  (:require [musicality.react-cmp :as rc]
            [musicality.osc :as o])
  (:import (clojure.lang Numbers)))

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
            :note-type  :note-type/JI
            :val        1
            :instrument "/organ"}

   :organ1 {
            :note-type  :note-type/JI
            :val        1
            :instrument "/organ2"}

   :fn     {
            :instrument "/fn"
            :note-type  :note-type/Function
            :val        "foo"
            }

   :cr78   {:note-type       :note-type/UnpitchedMidi
            :val             1
            :note-collection "CR78"
            :instrument      "/cr78"}
   :tr808  {:note-type       :note-type/UnpitchedMidi
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


(def cr78-samples
  {:cr78/BongoH  0
   :cr78/BongoL  1
   :cr78/CH3     2
   :cr78/CH5     3
   :cr78/Conga5  4
   :cr78/Conga7  5
   :cr78/Kick    6
   :cr78/KickHi  7
   :cr78/OH3     8
   :cr78/OH5     9
   :cr78/OH7     10
   :cr78/Perc17  11
   :cr78/Perc27  12
   :cr78/Rim     13
   :cr78/Snare13 14
   :cr78/Snare14 15})

(def tr808-samples
  {:tr808/BDLo    0
   :tr808/BDLong  1
   :tr808/BDHi    2
   :tr808/CH      3
   :tr808/Clap    4
   :tr808/Claves  5
   :tr808/Cowbell 6
   :tr808/Cymbal  7
   :tr808/Maracas 8
   :tr808/OH      9
   :tr808/Rim     10
   :tr808/SnareLo 11
   :tr808/SnareHi 12
   :tr808/TomHi   13
   :tr808/TomLo   14
   :tr808/TomMid  15})


(def _ nil)
(def . nil)
(def x 1)


; https://www.redblobgames.com/grids/hexagons/#hex-to-pixel-axial
(defn hex-pointy->xy [q r cell-size]
  [
   (float (* cell-size (+ (* (Math/sqrt 3) q)
                          (* r
                             (/ (Math/sqrt 3) 2)))))
   (float (* cell-size (* -3/2 r)))
   ]
  )

(defn hex-flat->xy [q r cell-size]
  [
   (float (* cell-size (+ (* (Math/sqrt 3) q)
                          (* r
                             1
                             (/ (Math/sqrt 3) 2)))))
   (float (* cell-size (* -3/2 r)))

   ]
  )


(defn pos-on-tonnetz [pos-center q r size]
  (let [[x y] (hex-flat->xy q r size)]
    [(+ x (nth pos-center 0))
     (+ y (nth pos-center 1))
     (nth pos-center 2)]))

(defn tonnegg-tonnetz [preset spawner-id pos-center q r val]
  (rc/tonnegg+
    (merge
      preset
      {
       :val              val
       :spawnFromSpawner (rc/transform
                           :pos (pos-on-tonnetz pos-center q r 0.06)
                           :sca [0.1 0.1 0.1])
       :spawnerId        spawner-id})))

(defn pos-on-circle
  "Returns a vec [x y z] representing the point on a unit circle centered at pos-center, at sector represented by a ratio. 1/4 is 3 o'clock"
  [pos-center sector]
  (let [r (Numbers/toRatio sector)
        a (* (numerator r)
             (/ (* 2 Math/PI)
                (denominator r)))
        x (* 0.5 (Math/sin a))
        y (* 0.5 (Math/cos a))
        ]
    [(+ x (nth pos-center 0))
     (+ y (nth pos-center 1))
     (nth pos-center 2)]))


(def ^{:deprecated "Use tonnegg-circ-seq+"}
  tonnegg-circ
  (fn [preset spawner-id pos-center sector val]
    (rc/tonnegg+
      (merge
        preset
        {
         :val              val
         :spawnFromSpawner (rc/transform
                             :pos (pos-on-circle pos-center sector)
                             :sca [0.1 0.1 0.1])
         :spawnerId        spawner-id}))))

(def ^{:deprecated "Use tonnegg-circ-seq+"}
  tonnegg-circ-pattern
  (fn [preset-kw spawner-id pos-center divisions val-fn pattern]
    (->> pattern
         (map-indexed
           (fn [i v]
             (when v
               (tonnegg-circ
                 (tonnegg-presets preset-kw)
                 spawner-id
                 pos-center
                 (/ i divisions)
                 (val-fn v)
                 ))))
         (doall))))



; TODO: pass beatwheel, use its position and spawner
(defn tonnegg-on-circle+
  [val & {:keys [
                 pos-center
                 preset-kw
                 sector
                 spawner-id
                 ]}]
  (rc/tonnegg+
    (merge
      (tonnegg-presets preset-kw)
      {
       :val              val
       :spawnFromSpawner (rc/transform
                           :pos (pos-on-circle pos-center sector)
                           :sca [0.1 0.1 0.1])
       :spawnerId        spawner-id})))

(defn tonnegg-circ-seq+
  [sq & {:keys [
                pos-center
                preset-kw
                spawner-id
                ]}]
  (->> sq
       (map-indexed
         (fn [i v]
           (when v
             (tonnegg-on-circle+ v
                                 :pos-center pos-center
                                 :preset-kw preset-kw
                                 :spawner-id spawner-id
                                 :sector (/ i (count sq))
                                 )
             )))
       (doall)))

(defn fn+ [& {:keys [
                     fn
                     fn-name
                     spawn-from-cam
                     spawn-from-spawner
                     spawner-id
                     ]}]
  (rc/tonnegg+ :fn fn
               :instrument "/fn"
               :note-type :note-type/Function
               :val fn-name
               :spawnFromCam (when spawn-from-cam (rc/transform spawn-from-cam))
               :spawnFromSpawner (when spawn-from-spawner (rc/transform spawn-from-spawner))
               :spawnerId spawner-id))

(defn organ+ [& {:keys [
                         diapason
                         partials-amplitudes
                         partials-intervals
                         partials-releases
                         preset-kw
                         spawn-from-cam
                         spawn-from-spawner
                         spawner-id
                         ]}]
  (rc/organ+
    ({
      :organ0 0
      :organ1 1}
     preset-kw)
    (merge (organ-presets preset-kw)
           {
            :diapason         diapason
            :partials         (->> (range 16)
                                   (map (fn [i]
                                          {:Interval  {:Val      (or (nth partials-intervals i) i)
                                                       :NoteType "Irrational"}
                                           :Amplitude (or (nth partials-amplitudes i) 0)
                                           :Release   (or (nth partials-releases i) 0)})))
            :spawnFromCam     (when spawn-from-cam (rc/transform spawn-from-cam))
            :spawnFromSpawner (when spawn-from-spawner (rc/transform spawn-from-spawner))
            :spawnerId        spawner-id
            })))

