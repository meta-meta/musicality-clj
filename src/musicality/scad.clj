(ns musicality.scad
  (:use [scad-clj.scad])
  (:use [scad-clj.model])
  (:use [scad-clj.text]))

(def pitch-class-order [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ])

(defn pitch-class "extrudes the character for a pitch-class"
  [pc & {:keys [h size]}]
  (extrude-linear {:height h}
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
                 (map #(seg-to-arc-vert % r1))))))

(defn chroma-circle
  [& {:keys [border
             pitch-class-radius
             pitch-class-font-size
             scale-degree-font-size]}]
  (union
   

   ;; Scale degree chord
   #_(->> [[0 "I"] [2 "ii"] [4 "iii"] [5 "IV"] [7 "V"] [9 "vi"] [11 "vii°"]]
        (map (fn [[pc role]]
               (->> (text role
                          :font "JetBrains Mono"
                          :size scale-degree-font-size :halign "center" :valign "center")
                    (extrude-linear {:height 0.2})
                    (color [0 0 0 1])
                    (rotate [0 0 (* pc (/ Math/PI -6))])
                    (translate-on-clockface (- pitch-class-radius pitch-class-font-size scale-degree-font-size)
                                            pc)
                    (translate [0 0 0.6])))))
   

   ;; Scale mask
   #_(->> [0 2 4 5 7 9 11]
        (map (fn [pc]
               (extrude-linear {:height 1}
                               (polygon
                                (arc-vertices
                                 (- pitch-class-radius (- pitch-class-font-size 1))
                                 (+ pitch-class-radius (- pitch-class-font-size 1))
                                 (* 0.9 (/ Math/PI 6))
                                 (* pc
                                    (/ (* 2 Math/PI) 12))
                                 12)))))
        (difference (with-fn 144 
                      (cylinder (+ pitch-class-radius border) 0.2)
                      (cylinder 1 1)))
        (translate [0 0 0.4])
        (color [1 1 1 0.5]))


   


   ;; Pitch-class digits
   (->> pitch-class-order
        (map-indexed
         (fn [i pc]
           (->> (pitch-class pc :h 0.4 :size pitch-class-font-size)
                (color [0 0 0 1])
                (translate-on-clockface pitch-class-radius i)
                (translate [0 0 0.2])))))

   ;; Outer Ring
   (with-fn 144
     (translate [0 0 0.3]
                (color [0 0 0 1]
                       (difference
                        (cylinder 4 0.4)
                        (cylinder 1 1)))))

   ;; Inner Ring
   (with-fn 144
     (translate [0 0 0.3]
                (color [0 0 0 1]
                       (difference
                        (cylinder (+ pitch-class-radius border) 0.4)
                        (cylinder (+ pitch-class-radius (* border 0.8)) 1)))))

   ;; Clockface
   (with-fn 144
       (color [1 1 1 1]
              (difference
               (cylinder (+ pitch-class-radius border) 0.3)
               (cylinder 1 1))))))

(spit "output.scad"
      (write-scad (chroma-circle
                   :border 4
                   :pitch-class-font-size 4
                   :pitch-class-radius 24
                   :scale-degree-font-size 3)))
