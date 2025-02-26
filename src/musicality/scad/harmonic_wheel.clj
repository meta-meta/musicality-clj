(ns musicality.scad.harmonic-wheel
  (:use [scad-clj.scad])
  (:use [scad-clj.model :as m])
  (:use [scad-clj.text])
  (:use [musicality.pc-sets])
  (:use [clojure.repl]))

(defn output [scad-data]
  (->> scad-data
       (write-scad)
       (spit "3d-print/output.scad")))


(defn ring-spheres [& {:keys [r-sphere r-ring count]}]
  (->>
    (range count)
    (map (fn [i] (let [theta (+ (* 5 count) (* 2 Math/PI (/ i count)))
                       r 10]
                   (->>
                     (m/sphere r-sphere)
                     (m/with-fn 20)
                     (m/translate [(* r-ring (Math/sin theta)) (* r-ring (Math/cos theta)) 0])
                     ))))
    (apply m/union))
  )


(->>
  (range 21)
  (map (fn [i] (ring-spheres
                 :count (+ 1 i)
                 :r-sphere 2
                 :r-ring (+ 10 (* i 4))
                 )))
  (apply m/union)
  (union (ring-spheres
           :count 1
           :r-sphere 1
           :r-ring 0
           ))
  (difference (m/cylinder 100 1))


  (output))


(output (m/cylinder 100 1))