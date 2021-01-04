(ns musicality.compositions.2021-01-03-14.01.53
  (:use [musicality.compose :as c]
        [musicality.pc-sets :as p]
        [musicality.schedule :as s]))

#_(s/init)
#_(s/deinit)

#_(s/send-beat 1 :note [60 64])
#_(s/clear)

(defn send-chord [coll]
  (s/send-beat 1 :note (chord coll)) )

(defn send-scale [coll]
  (->> coll
       (map-indexed (fn [i n] (s/send-beat (+ 1 i) :note [n 64])))
       (doall)) )

(defn rand-pc-set [pc-count send-fn]
  (let [pc-set (->> (p/find-pc-set pc-count "")
                    (rand-nth))]
    (->> pc-set
         (:prime-form)
         (map c/pc->int)
         (map #(+ 60 %))
         (send-fn)
         )
    pc-set)
  )

(defn play-random-chord [pc-count]
  (rand-pc-set pc-count send-chord))

(defn play-random-scale [pc-count]
  (rand-pc-set pc-count send-scale))

(comment "play some random chords and scales"
  
  (play-random-chord 4)
  (play-random-chord 5)  
  (play-random-scale 5)
  
)
