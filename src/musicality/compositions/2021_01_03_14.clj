(ns musicality.compositions.2021-01-03-14
  (:use [musicality.compose :as c]
        [musicality.pc-sets :as p]
        [musicality.schedule :as s]))

#_(s/init)
#_(s/init "192.168.1.12")
#_(s/deinit)

#_(s/send-beat 1 :note [60 64])
#_(s/clear)

(defn send-chord
  ([coll beat sub-beat]
   (s/send-beat beat sub-beat :note (chord coll)))
  ([coll beat] (send-chord coll beat 1))
  ([coll] (send-chord coll 1 1)))

(defn send-scale [coll]
  (->> coll
       (map-indexed (fn [i n] (s/send-beat (+ 1 i) :note [n 64])))
       (doall)))

(defn rand-pc-set [pc-count send-fn]
  (let [pc-set (->> (p/find-pc-set pc-count "")
                    (rand-nth))]
    (->> pc-set
         (:prime-form)
         (map c/pc->int)
         (map #(+ 60 %))
         (send-fn))
    pc-set))

(defn play-random-chord [pc-count]
  (rand-pc-set pc-count send-chord))

(defn play-random-scale [pc-count]
  (rand-pc-set pc-count send-scale))

(defn pc? [x] (contains? c/pcs x))

(defn to-octave [x o]
  (let [n (mod (if (pc? x)
                 (c/pc->int x)
                 x)
               12)]
    (+ n (* 12 (+ 1 o)))))

(defn pc-set->ints
  ([pc-set-id rot pc-rot n0]
   (->> (if (keyword? pc-set-id)
          (p/get-pc-set pc-set-id)
          (:prime-form (nth p/pc-sets pc-set-id)))
        (c/rotate-seq rot)
        (map #(c/pc-rotate % pc-rot))
        (map c/pc->int)
        (map #(+ n0 %))))
  ([pc-set-id n0] (pc-set->ints pc-set-id 0 0 n0)))


(defn send-prog-pc-sets-over-ints
  "generates and sends a chord progression based on a carrier scale
  of ints and seq of tuples of [root pc-set]"
 [carrier prog]
  (clear)
  (->> prog
       (map (fn [[degree pc-set]]
              (pc-set->ints pc-set
                            (nth carrier (- degree 1)))))
       (map-indexed (fn [i chord]
                      (send-chord chord (+ 1 (* 2 i)))))))



(comment "play some random chords and scales"
         (clear)
         (play-random-chord 4)
         (play-random-chord 5)
         (play-random-scale 5)
         (play-random-scale 7)

         (nth p/pc-sets  276)

         (send-prog-pc-sets-over-ints
          (pc-set->ints 276 1 -1 60)
          [[2 :min] [5 :maj] [1 :maj]])
         ; TODO: make one that uses degrees of the carrier chord-scale
         ; TODO: accept literals

         ; TODO: set carrier scale in state atom, schedule fns that play chords based on current state. send notes for immediate playback instead of scheduling (how much latency will this add?)

         (send-scale
          (pc-set->ints 276 60))

         (send-scale
          (pc-set->ints 276 1 -1 60)))
