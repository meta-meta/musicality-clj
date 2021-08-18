(ns musicality.compositions.2021-08-17
  (:require [musicality.compose :as c]
            [musicality.schedule :as s]
            [musicality.pc-sets :as p]
            [musicality.symbols :refer :all]))

(def beats (atom {}))

(defn clear []
  (s/clear "drums")
  (reset! beats  {}))

(defn set-len [x] (s/send-beat-count "drums" x))

(defn send-beats [beats]
  (clear)
  (s/send-beats "drums" beats))

#_(clear)
#_(set-len (* 4 24))

(defn merge-notes [& args]
  (apply merge-with
         (fn [old new]
           #_(prn "old" old "new" new)
           (merge-with clojure.set/union old new))
         args))

(defn gcd
  [a b]
  (if (zero? b)
    a
    (recur b, (mod a b))))

(defn lcm
  ([a b]
   (/ (* a b) (gcd a b)))
  ([a b & v] (reduce lcm (concat [a b] v))))

(defn lcm-meter [& flat-pairs]
  (let [pairs (partition 2 flat-pairs)
        len (->> pairs
                 (map #(count (first %)))
                 (apply lcm))]

    (->> pairs
         (map (fn [[bin-rhy multiply-type]]
                (let [mult (/ len (count bin-rhy))]
                  (cond
                    (= multiply-type :repeat)
                    (c/repeat mult bin-rhy)

                    (= multiply-type :expand)
                    (c/expand mult bin-rhy))))))))

#_(lcm-meter
   [x . x] :repeat
   [x . x .] :expand)

#_(lcm-meter
   [x . x] :expand
   [x . x . .] :repeat)

(lcm-meter
 [x .] :expand
 [. x] :expand
 [x . x] :repeat)

(defn lcm-meter-with-opts [& triples]
  (let [trips (partition 3 triples)
        rhys (->> trips
                  (mapcat #(take 2 %))
                  (apply lcm-meter))
        opts-all (->> trips
                      (map last))]

    (->> (map vector rhys opts-all)
         (reduce (fn [beats [bin-rhy opts]]
                   (->> (apply c/bin->rhy (conj opts bin-rhy))
                        (map-indexed (fn [i n] (if (empty? n) nil  [i {:note #{n}}])))
                        (filter identity)
                        (into {})
                        (merge-notes beats))) {}))))




; Two Hearts
(set-len 6)
(->> (lcm-meter-with-opts
      [x .] :expand [6 70]
      [. x] :expand [[14 15] [20 30]]
      [x . x] :repeat [3 [60 20]])
     (send-beats))

; Purdy Shuffle
(set-len 12)
(->> (lcm-meter-with-opts
      [x . x] :repeat [3 [60 10]]
      [. x .] :repeat [14 10]
      [. . . . . . x . . . . .] :expand [15 70]
      [x . . . . x . . . . . x] :expand [6 70]
      )
     (send-beats))


; Jazz practice 3:4
(set-len 12)
(->> (lcm-meter-with-opts
      [x . . x . x] :repeat [10 70]
      [x x x x] :expand [6 70]
      [x x x .] :repeat [14 [10 10 10]])
     (send-beats))


; Bembe practice 3:4
(set-len 12)
(->> (lcm-meter-with-opts
      (c/rotate-seq 0 [x . x . x x . x . x . x]) :repeat [1 50]
      [. x . x] :expand [3 50]
      [x x x x] :expand [6 90]
      [x x x .] :repeat [14 [10 10 10]]
      )
     (send-beats))


; 12:7:5
(clear)
(set-len 60)
(->> (lcm-meter-with-opts
      [x . x . x x . x . x . x] :repeat [0 50]
      [x . x . x . x] :repeat [1 50]
      [x . x . x] :repeat [3 40]
      )
     (send-beats))



(comment "long-hand merging beats individually"

         (->> [x .]
              (c/expand 3)
              (c/repeat 4)
              (c/expand 4)
              (c/bin->rhy 6 70)
              (map-indexed (fn [i n] (if (empty? n) nil  [(+ 1 i) {:note #{n}}])))
              (filter identity)
              (into {})
              (swap! beats merge-notes)
              (s/send-beats "drums"))

         (->> [. x]
              (c/expand 3)
              (c/repeat 4)
              (c/expand 4)
              (c/bin->rhy [14 15] [20 30])
              (map-indexed (fn [i n] (if (empty? n) nil  [(+ 1 i) {:note #{n}}])))
              (filter identity)
              (into {})
              (swap! beats merge-notes)
              (s/send-beats "drums"))

         (->> [x . x]
              (c/repeat 8)
              (c/expand 4)
              (c/bin->rhy 3 [60 20])
              (map-indexed (fn [i n] (if (empty? n) nil  [(+ 1 i) {:note #{n}}])))
              (filter identity)
              (into {})
              (swap! beats merge-notes)
              (s/send-beats "drums"))

         (clear)

)

#_(s/deinit)
