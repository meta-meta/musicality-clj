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





(def cr78 {
           :bongo-hi 0
           :bongo-lo 1
           :hihat-closed-1 2
           :hihat-closed-2 3
           :conga-1 4
           :conga-2 5
           :kick-lo 6
           :kick-hi 7
           :hihat-open-1 8
           :hihat-open-2 9
           :hihat-open-3 10
           :perc-1 11
           :perc-2 12
           :snare-rim 13
           :snare-1 14
           :snare-2 15
})


; Two Hearts
(set-len 6)
(->> (lcm-meter-with-opts
      [x . x] :repeat [(:hihat-closed-2 cr78) [60 20]]
      [x .] :expand [(:kick-lo cr78) 70]
      [. x] :expand [[(:snare-1 cr78) (:snare-2 cr78)] [20 30]]
     )
     (send-beats))

; Purdy Shuffle
(set-len 12)
(->> (lcm-meter-with-opts
      [x . x] :repeat [(:hihat-closed-2 cr78) [60 10]]
      [. x .] :repeat [(:snare-1 cr78) 10]
      [. . . . . . x . . . . .] :expand [(:snare-2 cr78) 70]
      [x . . . . x . . . . . x] :expand [(:kick-lo cr78) 70]
      )
     (send-beats))


; Jazz practice 3:4
(set-len 12)
(->> (lcm-meter-with-opts
      [x . . x . x] :repeat [(:hihat-open-3  cr78) 70]
      [x x x x] :expand [(:kick-lo cr78) 70]
      [x x x .] :repeat [(:snare-1 cr78) [10 10 10]])
     (send-beats))


; Bembe practice 3:4
(set-len 12)
(->> (lcm-meter-with-opts
      (c/rotate-seq 0 [x . x . x x . x . x . x]) :repeat [(:bongo-lo cr78) 50]
      [. x . x] :expand [(:hihat-closed-2 cr78) 50]
      [x x x x] :expand [(:kick-lo cr78) 90]
      [x x x .] :repeat [(:snare-1 cr78) [10 10 10]]
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
