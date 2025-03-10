(ns musicality.scad.pc-clock
  (:use [scad-clj.scad])
  (:use [scad-clj.model])
  (:use [scad-clj.text])
  (:use [musicality.pc-sets])
  (:use [clojure.repl]))




(defn output [scad-data]
  (->> scad-data
       (write-scad)
       (spit "3d-print/output.scad")))

#_(->> (cube 1 2 3 :center true)
       (output))

#_(->> (sphere 5)
       (with-fn 10)
       (output))

(def pitch-class-order [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ])

(defn pitch-class "extrudes the character for a pitch-class"
  [pc & {:keys [h size]}]
  (extrude-linear {:height h :center false}
                  (text (name pc)
                        :size size
                        :font "JetBrains Mono" ; JetBrains Mono added to ~/.fonts folder
                        :halign "center"
                        :valign "center")))

(defn translate-on-clockface "translates an object to a position at n-o'clock at radius r"
  [r n rotate? obj]
  (let [theta (* n (/ Math/PI 6))]
    (->> obj
         (rotate [0 0 (if rotate? (* -1 theta) 0)])
         (translate [(* r (Math/sin theta))
                     (* r (Math/cos theta))
                     0]))))

#_(->> (range 12)
       (map #(->> (pitch-class (nth pitch-class-order %) :h 0.1 :size 1)
                  (translate-on-clockface 5 false %)))
       (output))

(defn arc-vertices "returns a vec of [[x y] ...] consisting of vertices of an arc"
  [r1 r2 arc-length center-theta segs]
  (let [seg-length (/ arc-length segs)
        verts (range (+ 1 segs))
        seg-to-arc-vert (fn [seg r]
                          (let [theta (+ center-theta
                                         (- (* seg seg-length)
                                            (/ arc-length 2)))]
                            [(* r (Math/sin theta)) (* r (Math/cos theta))]))]
    (concat (->> verts
                 (map #(seg-to-arc-vert % r2)))
            (->> verts
                 (reverse)
                 (map #(seg-to-arc-vert % r1)))
            [(seg-to-arc-vert 0 r2)])))

#_(->> (arc-vertices 1 3 (/ Math/PI 3) 0 3)
       (polygon)
       (output))

(defn ring-of-spheres  "returns 12 spheres of radius r-sphere arranged in a ring of radius r. no-rotate? specifies whether to rotate each sphere such that its pole is aligned with the ring's center"
  [r r-sphere & {:keys [no-rotate?]}]
  (->> (range 12)
       (map #(translate-on-clockface
              r % true
              (->> (sphere r-sphere)
                   (rotate [(if no-rotate?
                              0
                              (/ Math/PI 2))
                            0
                            (* % (/ Math/PI -6))]))
               ))
       (union)))

#_(output (ring-of-spheres 5 1 :no-rotate? false))

(defn hub-height [sphere-radius] (* sphere-radius 2.5))
(def hub-gap-radius 0.25)
(def sphere-gap-radius 0.2)

(defn hub-inner [& {:keys [clockface-thickness
                           sphere-radius
                           hub-radius
                           spacer-ring-thickness]}]

  (let [r-ring (- hub-radius (* 1.5 sphere-radius))
        r-cup (+ sphere-radius sphere-gap-radius)
        body (->> (cylinder hub-radius
                            (+ (hub-height sphere-radius)
                               clockface-thickness
                               spacer-ring-thickness)
                            :center false)
                  (with-fn 144))
        cutout (->>
                (union
                 ;; cup
                 (->> (ring-of-spheres r-ring r-cup :no-rotate? true)
                      (translate [0 0 r-cup]))

                 ;; aperture
                 (ring-of-spheres r-ring (* 1.075 sphere-radius) :no-rotate? true))
                (with-fn 36))]

    (-> (->> body
             (color [1 1 1 0.5]))
        (difference cutout))))



#_(->>  (hub-inner :clockface-thickness 1
                   :sphere-radius 0.2
                   :hub-radius 2
                   :spacer-ring-thickness 0.5)
        (output))



(defn hub-outer [& {:keys [clockface-thickness
                           sphere-radius
                           hub-radius
                           spacer-ring-thickness]}]
  (let [height (hub-height sphere-radius)
        outer-z (* sphere-radius 1.25)

        body (with-fn 36
               (union (with-fn 144 (color [1 1 1 0.5] (cylinder 12 0.4 :center false)))
                      (->> (ring-of-spheres
                            (+ (* sphere-radius 0.5) hub-radius)
                            (* 2.2 sphere-radius))
                           (scale [1 1 0.75])
                           (color [0 0 0 0.75])
                           (translate [0 0 outer-z]))))

        cavities (with-fn 36
                   (->> (union
                         ;; cup
                         (ring-of-spheres (+ (* 1.1 sphere-radius) hub-radius)
                                          (+ sphere-radius
                                             sphere-gap-radius))
                         ;; aperture
                         (ring-of-spheres hub-radius (* 1.075 sphere-radius)))
                        (translate [0 0 outer-z])))]

    (-> body
        (difference cavities)

        ; cutout for hub-inner
        (difference (with-fn 144
                      (cylinder (+ hub-radius hub-gap-radius) (* height 10))))
        ; crop the top
        (difference (->> (cylinder 12 1 :center false)
                         (translate [0 0 height]))))))



#_(->>  (hub-outer :clockface-thickness 0.1
                   :sphere-radius 0.5
                   :hub-radius 2
                   :spacer-ring-thickness 0.5)
        (output))



(defn pc-cf-digits [& {:keys [font-thickness
                              font-size
                              radius]}]
  (->> pitch-class-order
       (map-indexed
        (fn [i pc]
          (->> (pitch-class pc
                            :h font-thickness
                            :size font-size)
               (color [0 0 0 1])
               (translate-on-clockface radius i false))))))

(defn pc-clockface
  [& {:keys [border
             clockface-thickness
             pitch-class-font-thickness
             pitch-class-radius
             pitch-class-font-size
             sphere-radius]}]

  (let [hub-radius (* 6 sphere-radius)
        spacer-ring-thickness (* 1.25 pitch-class-font-thickness)]
    (union
  
     ;; Pitch-class digits
     (->> (pc-cf-digits :font-thickness pitch-class-font-thickness
                        :font-size pitch-class-font-size
                        :radius pitch-class-radius)
          (translate [0 0 clockface-thickness]))


     ;; Inner hub
     (hub-inner
      :clockface-thickness clockface-thickness
      :sphere-radius sphere-radius
      :hub-radius hub-radius
      :spacer-ring-thickness spacer-ring-thickness)

     ;; Inner Ring
     (->> (difference
           (cylinder (+ hub-radius (* sphere-radius 2.75))
                     spacer-ring-thickness
                     :center false)
           (cylinder hub-radius
                     (* pitch-class-font-thickness 10)))
          (with-fn 144)
          (translate [0 0 clockface-thickness])
          (color [0 0 0 1]))


     ;; Outer Ring
     (->> (difference (cylinder (+ pitch-class-radius border)
                                spacer-ring-thickness
                                :center false)
                      (cylinder (+ pitch-class-radius (* border 0.8))
                                (* spacer-ring-thickness 200)
                                :center true))
          (with-fn 144)
          (translate [0 0 clockface-thickness])
          (color [0 0 0 1]))

     ;; Back     
     (-> (->> (cylinder (+ pitch-class-radius border)
                        clockface-thickness
                        :center false)
              (with-fn 144 )
              (color [1 1 1 1] ))
         (difference (cylinder hub-radius (* 10 clockface-thickness)))))))




(defn pc-mask
  [& {:keys [border
             clockface-thickness
             pitch-classes
             zero-label?
             pitch-class-font-thickness
             pitch-class-radius
             pitch-class-font-size
             scale-degree-font-size
             scale-degree-radius
             sphere-radius]}]

  (let [hub-radius (* 6 sphere-radius)
        spacer-ring-thickness (* 1.25 pitch-class-font-thickness)]
    (union

     
     (translate [0 0 (+ clockface-thickness spacer-ring-thickness)]
                (union

                 ;; Outer Ring
                 (->> (difference (cylinder (+ 1.2 pitch-class-radius border scale-degree-font-size)
                                            0.2
                                            :center false)
                                  (cylinder (- (+ 1 pitch-class-radius border scale-degree-font-size) 0.2)
                                            10))
                      (with-fn 144)
                      (translate [0 0 clockface-thickness])
                      (color [0 0 0 1]))


                 ;; deltas -
                 (->> pitch-classes
                      (filter #(if zero-label? true (not= 0 %)))
                      (map (fn [pc]
                             (->> (text (str "-" (mod (- 12 pc) 12))
                                        :font "JetBrains Mono"
                                        :size (* 0.8 scale-degree-font-size) :halign "center" :valign "center")
                                  (extrude-linear {:height 0.2 :center false})
                                  (color [0 0 0 1])
                                  (translate-on-clockface
                                   scale-degree-radius
                                   pc
                                   true)
                                  (translate [0 0 0.4])))))

                 ;; deltas +
                 (->> pitch-classes
                     (filter #(if zero-label? true (not= 0 %)))
                     (map (fn [pc]
                             (->> (text (str "+" pc)
                                        :font "JetBrains Mono"
                                        :size (* 0.8 scale-degree-font-size) :halign "center" :valign "center")
                                  (extrude-linear {:height 0.2 :center false})
                                  (color [0 0 0 1])
                                  (translate-on-clockface 
                                   (+ (* 0.7 scale-degree-font-size)
                                      pitch-class-radius
                                      pitch-class-font-size)
                                   pc
                                   true)
                                  (translate [0 0 0.4])))))


                 ;; chromatic mask

                 (with-fn 144
                   (as-> (cylinder (+ 1.2 pitch-class-radius border scale-degree-font-size) 0.4 :center false) v

                     (color [1 1 1 0.5] v)
                     (difference v (cylinder 12 1))
                     (difference v
                                 (->> pitch-classes ; PC window
                                      (map (fn [pc]
                                             (->> (arc-vertices
                                                   (- pitch-class-radius (- pitch-class-font-size 1))
                                                   (+ pitch-class-radius (- pitch-class-font-size 1))
                                                   (* 0.9 (/ Math/PI 6))
                                                   (* pc
                                                      (/ (* 2 Math/PI) 12))
                                                   12)
                                                  (polygon)
                                                  (extrude-linear {:height 2}))))))
                     (union v (->> pitch-classes ; PC border
                                   (map (fn [pc]
                                          (->> (arc-vertices
                                                (- pitch-class-radius (- pitch-class-font-size 1) 0.3)
                                                (+ pitch-class-radius (- pitch-class-font-size 1) 0.3)
                                                (* 0.95 (/ Math/PI 6))
                                                (* pc
                                                   (/ (* 2 Math/PI) 12))
                                                12)
                                               (concat (reverse (arc-vertices
                                                                 (- pitch-class-radius (- pitch-class-font-size 1))
                                                                 (+ pitch-class-radius (- pitch-class-font-size 1))
                                                                 (* 0.9 (/ Math/PI 6))
                                                                 (* pc
                                                                    (/ (* 2 Math/PI) 12))
                                                                 12)))
                                               (polygon)
                                               (extrude-linear {:height 0.2 :center false})
                                               (translate [0 0 0.4])
                                               (color [0 0 0 1]))))))))

                 ;; outer hub
                 (->> (difference (hub-outer
                                   :clockface-thickness clockface-thickness
                                   :sphere-radius sphere-radius
                                   :hub-radius hub-radius
                                   :spacer-ring-thickness spacer-ring-thickness)
                                  (->> (cylinder (/ pitch-class-radius 2) 10 :center false)
                                       (translate [0 0 -10] )))
                      )))

    )))








;;; Big clock for 0.6 nozzle


#_(output (chroma-circle 1 nil
                         :border 18
                         :clockface-thickness 0.6
                         :pitch-class-font-size 18
                         :pitch-class-font-thickness 2
                         :pitch-class-radius 70
                         :scale-degree-font-size 3.4
                         :scale-degree-radius 17
                         :sphere-radius 1.5))




;; TODO -- move inner-hub cavities in slightly
;; TODO -- add some padding to inner-hub to move the bottom up even with outer ring


#_(->> (union
        (intersection
         (->> (arc-vertices 2 10 (/ Math/PI 6) 0 12)
              (polygon)
              (extrude-linear {:height 5}))
         (hub true false))

        (intersection
         (->> (arc-vertices 2 200 (/ Math/PI 6) 0 12)
              (polygon)
              (extrude-linear {:height 5}))
         (hub true true)))
       (output))




; mask for 0.2mm nozzle
#_(->> (pc-mask
        :border 5
        :clockface-thickness 0.3
        :pitch-classes [0 2 4 6 8 10]
        :pitch-class-font-size 4.5
        :pitch-class-font-thickness 0.3
        :pitch-class-radius 24
        :scale-degree-font-size 3.4
        :scale-degree-radius 17
        :sphere-radius 1.25)
       (output))

; clockface for 0.2mm nozzle
#_(->> (pc-clockface
        :border 5
        :clockface-thickness 0.3
        :pitch-classes [0 4 7 10]
        :pitch-class-font-size 4.5
        :pitch-class-font-thickness 0.3
        :pitch-class-radius 24
        :scale-degree-font-size 3.4
        :scale-degree-radius 17
        :sphere-radius 1.25)
       (output))


(defn str-on-clockface [{:keys [h r s font font-size clock-time] :as opts}]
  (->> (text s
             :font font
             :size font-size :halign "center" :valign "center")
       (extrude-linear {:height h :center false})
       (translate-on-clockface r clock-time true)
       ))

(defn mu-major []
  (->> 
   (apply difference
          (pc-mask
           :border 5
           :clockface-thickness 0.3
           :pitch-classes [0 2 4 7]
           :pitch-class-font-size 4.5
           :pitch-class-font-thickness 0.3
           :pitch-class-radius 24
           :scale-degree-font-size 3.4
           :scale-degree-radius 17
           :sphere-radius 1.25
           :zero-label? false)
          

          (->> (clojure.string/split "mu-maj" #"")
               (map-indexed (fn [i s]
                              (->> (str-on-clockface
                                    {:s s
                                     :font (if (= s "μ") "JetBrains Mono" "Paper")
                                     :font-size 3
                                     :r 30
                                     :clock-time (+ 11.4 (/ i 4))
                                     :h 10
                                     })
                                   (color [0 0 0 1])))))

          #_[(->> (str-on-clockface
                   {:s "asd"
                    :font-size 5
                    :r 16
                    :clock-time 10
                    :h 10
                    })
                  (translate [0 0 0.4]))]
          #_[(->> (text "μ-major"
                        :font "JetBrains Mono"
                        :size 5 :halign "center" :valign "center")
                  (extrude-linear {:height 10 :center false})
                  (color [0 0 0 1])
                  #_(rotate [0 0 (* 9 (/ Math/PI -6))])
                  (translate-on-clockface 16 9.5 true)
                  (translate [0 0 0.4]))] )
 )) 






(->> (mu-major)
     (output))

#_(->>
 (pc-clockface
        :border 5
        :clockface-thickness 0.3
        :pitch-classes [0 4 7 10]
        :pitch-class-font-size 4.5
        :pitch-class-font-thickness 0.3
        :pitch-class-radius 24
        :scale-degree-font-size 3.4
        :scale-degree-radius 17
        :sphere-radius 1.25)
 (translate [0 0 -1])
#_ (union (mu-major))
 (output))
