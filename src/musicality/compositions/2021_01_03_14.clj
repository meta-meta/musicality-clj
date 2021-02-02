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


(defn rand-pc-set
  "returns a map containing 
  :pc-set - record from pc-sets
  :ints - result of applying pc-set to 
  "
 [pc-count send-fn n0]
  (let [pc-set (->> (p/find-pc-set pc-count "")
                    (rand-nth))]
    {:pc-set pc-set
     :ints (->> pc-set
                (:prime-form)
                (map c/pc->int)
                (map #(+ n0 %)))}))

(defn play-random-chord [pc-count n0]
  (->> n0
       (rand-pc-set pc-count n0)
       (:ints)
       (send-chord)))

(defn play-random-scale [pc-count n0]
  (->> n0
       (rand-pc-set pc-count send-scale)
       (:ints)
       (send-scale)))

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
         (play-random-chord 5 50)

         (play-random-scale 5 50)
         (play-random-scale 7 60)


         ; TODO: send metadata source abstractions to scheduler
         ; context: pc-set 276 centered at 1
         ; seq: 1^ :maj 2^ :min 4^ :maj 5^ :maj
         
         
         (send-prog-pc-sets-over-ints
          (pc-set->ints 276 1 -1 (+ 4 57))
          [[1 :maj] [2 :min] [4 :maj] [5 :maj]])

         (p/find-pc-set 4 "maj")

         (nth p/pc-sets  276)

         
         ; TODO: make one that uses degrees of the carrier chord-scale
         ; TODO: accept literals

         ; TODO: set carrier scale in state atom, schedule fns that play chords based on current state. send notes for immediate playback instead of scheduling (how much latency will this add?)

         (send-scale
          (pc-set->ints 276 60))

         (send-scale
          (pc-set->ints 276 1 -1 60))



;; Use this as your DJ for the weekend.



)
