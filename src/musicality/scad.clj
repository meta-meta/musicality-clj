(ns musicality.scad
  (:use [scad-clj.scad])
  (:use [scad-clj.model])
  (:use [scad-clj.text]))

(def pitch-class-order [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ])

(defn pitch-class "extrudes the character for a pitch-class"
  [pc & {:keys [h size]}]
  (extrude-linear {:height h :center false}
                  (text (name pc)
                        :size size
                        :font (if (contains? #{:૪ :Ɛ} pc)
                                "Segoe UI"
                                "JetBrains Mono") ; JetBrains Mono added to ~/.fonts folder
                        :halign "center"
                        :valign "center")))

(defn translate-on-clockface "translates an object to a position at n-o'clock at radius r"
  [r n obj]
  (let [theta (* n (/ Math/PI 6))]
    (translate [(* r (Math/sin theta))
                (* r (Math/cos theta))
                0]
               obj)))

(defn arc-vertices "returns a vec of [x,y] consisting of vertices of an arc"
  [r1 r2 arc-length center-theta segs]
  (let [seg-length (/ arc-length segs)
        verts (range (+ 1 segs))
        seg-to-arc-vert (fn [seg r]
                          (let [theta (+ center-theta
                                         (- (* seg seg-length)
                                            (/ arc-length 2)))]
                            (map #(* r %) [(Math/sin theta) (Math/cos theta)])))]
    (concat (->> verts
                 (map #(seg-to-arc-vert % r2)))
            (->> verts
                 (reverse)
                 (map #(seg-to-arc-vert % r1)))
            [(seg-to-arc-vert 0 r2)]
            )))

(defn ring-of-spheres [r r-sphere & {:keys [no-rotate?]}]
  (->> (range 12)
       (map #(translate-on-clockface r %
                                     (rotate [(if no-rotate?
                                                0
                                                (/ Math/PI 2))
                                              0
                                              (* % (/ Math/PI -6))]
                                             (sphere r-sphere))))
       (union)))


(defn hub [inner outer]
  (let [gap-radius 0.5
        height 4
        inner-radius 8.6
        sphere-radius 1.5
        sphere-gap-radius 0.2
        outer-z (* sphere-radius 1.2)
]

    (with-fn 144
      (union

       ;; Inner Hub
       (when inner

         (let [r (- inner-radius (* 1.3 sphere-radius))
               cup-r (+ sphere-radius sphere-gap-radius)]

           (as-> (cylinder inner-radius (+ 0.6 0.4  (* 2 sphere-radius)) :center false) v
             (with-fn 36
             (difference v 
                         (union
                          ;; cup
                          (translate [0 0 cup-r]
                                     (ring-of-spheres r
                                                      cup-r
                                                      :no-rotate? true))
                          
                          ;; aperture
                          (ring-of-spheres r
                                           (* 1.075 sphere-radius)
                                           :no-rotate? true))))
             )))

       #_(ring-of-spheres 7.4 sphere-radius)


       ;; Outer Hub
       (when outer
         

                    ;; buckyballs (don't print)

         #_(translate [0 0 outer-z] 
                    (color [1 0 1 1]
                           (ring-of-spheres (+ inner-radius sphere-radius) sphere-radius)))
                    

         (difference
          (with-fn 36
            (difference
             (union (with-fn 144 (cylinder 12 0.4 :center false))
                    (translate [0 0 outer-z]
                               
                               (scale [1 1 0.75] (ring-of-spheres (+ (* sphere-radius 0.5) inner-radius)
                                                                  (* 2.2 sphere-radius)))))

             (translate [0 0 outer-z]
                        (union
                         ;; cup
                         (ring-of-spheres (+ (* 1.1 sphere-radius) inner-radius)
                                          (+ sphere-radius
                                             sphere-gap-radius))
                         ;; aperture
                         (ring-of-spheres inner-radius (* 1.075 sphere-radius))))))
                                        ;(cylinder 13.5 4)
          (cylinder (+ inner-radius gap-radius) (* height 10)) ; inner circle
          (translate [0 0 (+ 0.6  (* 2 sphere-radius))]  ; top-crop
                     (cylinder 12 1 :center false))
))))))

(defn chroma-circle
  [clockface mask
   & {:keys [border
             clockface-thickness
             pitch-class-font-thickness
             pitch-class-radius
             pitch-class-font-size
             scale-degree-font-size
             scale-degree-radius]}]
  (union

   (when mask
     (union

      ;; Scale degree chord
      (->> [[0 "I"] [2 "ii"] [4 "iii"] [5 "IV"] [7 "V"] [9 "vi"] [11 "vii°"]]
           (map (fn [[pc scale-degree]]
                  (->> (text scale-degree
                             :font "JetBrains Mono"
                             :size scale-degree-font-size :halign "center" :valign "center")
                       (extrude-linear {:height 0.2 :center false})
                       (color [0 0 0 1])
                       (rotate [0 0 (* pc (/ Math/PI -6))])
                       (translate-on-clockface scale-degree-radius #_(- pitch-class-radius
                                                                        pitch-class-font-size
                                                                        scale-degree-font-size)
                                               pc)
                       (translate [0 0 0.4])))))

      ;; outer hub
      (color [1 1 1 0.75]
             (difference (hub false true)
                         (translate [0 0 -10] (cylinder pitch-class-radius 10 :center false))))
              

      (with-fn 144 ; Scale mask
        (as-> (cylinder (+ 1 pitch-class-radius border) 0.4 :center false) v
          
          (color [1 1 1 0.5] v)
          (difference v (cylinder 12 1))          
          (difference v
                      (->> [0 2 4 5 7 9 11] ; PC window
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
          (union v (->> [0 2 4 5 7 9 11] ; PC border
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
                                       (color [0 0 0 1]))))))

          ))))

   (when clockface
     (union

      ;; Pitch-class digits
      (->> pitch-class-order
           (map-indexed
            (fn [i pc]
              (->> (pitch-class pc
                                :h pitch-class-font-thickness
                                :size pitch-class-font-size)
                   (color [0 0 0 1])
                   (translate-on-clockface pitch-class-radius i)
                   (translate [0 0 clockface-thickness])))))



      ;; Inner hub

      
      
      (hub true false) 


;; Magnet center for big clock
      #_(with-fn 144
        (color [0 0 0 1]
               (translate [0 0 clockface-thickness]
                          (difference
                           (cylinder 10 4 :center false)
                           (cylinder 9 10)))))

      ;; Outer Ring
      (with-fn 144
        (translate [0 0 clockface-thickness]
                   (color [0 0 0 1]
                          (difference
                           (cylinder (+ pitch-class-radius border)
                                     (* pitch-class-font-thickness 1.25)
                                     :center false)
                           (cylinder (+ pitch-class-radius (* border 0.8))
                                     (* pitch-class-font-thickness 10))))))

      ;; Clockface
      (with-fn 144
        (as-> (cylinder (+ pitch-class-radius border)
                        clockface-thickness
                        :center false) v
          (color [1 1 1 1] v)
          (difference v (cylinder 8.6 (* 10 clockface-thickness))) ;hub inner radius
          ))))))


(spit "3d-print/output.scad"
      (write-scad (chroma-circle nil 1
                                 :border 5
                                 :clockface-thickness 0.3
                                 :pitch-class-font-size 4.5
                                 :pitch-class-font-thickness 0.3
                                 :pitch-class-radius 24
                                 :scale-degree-font-size 3.4
                                 :scale-degree-radius 17)))

;;; Big clock for 0.6 nozzle
#_(spit "3d-print/output.scad"
      (write-scad (chroma-circle 1 nil
                                 :border 18
                                 :clockface-thickness 0.6
                                 :pitch-class-font-size 18
                                 :pitch-class-font-thickness 2
                                 :pitch-class-radius 70
                                 :scale-degree-font-size 3.4
                                 :scale-degree-radius 17)))


;; TODO -- move inner-hub cavities in slightly
;; TODO -- add some padding to inner-hub to move the bottom up even with outer ring


#_(spit "output.scad"
        (write-scad (union
                     (intersection
                      (->> (arc-vertices 2 10 (/ Math/PI 6) 0 12)
                           (polygon)
                           (extrude-linear {:height 5}))
                      (hub true false))

                     (intersection
                      (->> (arc-vertices 2 200 (/ Math/PI 6) 0 12)
                           (polygon)
                           (extrude-linear {:height 5}))
                      (hub true true)))))
