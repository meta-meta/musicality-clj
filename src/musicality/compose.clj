(ns musicality.compose
  (:gen-class))

(def pcs "pitch-class keywords" #{:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ})
;; TODO (annoying that :0 is not highlighted with clojure-keyword-face)
;; https://stackoverflow.com/questions/39192226/create-a-keyword-from-a-number  - maybe not highlighting because it's in a grey area

(def pcs-ordered "pitch-class keywords in order" [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ])

(def pc->int "returns int corresponding to pitch-class keyword"
  (zipmap pcs-ordered (range)))

(defn int->pc "returns pitch-class keyword corresponsing to int"
  [x] (nth pcs-ordered (mod x (count pcs-ordered)) x))

(def pcs-cycle-up "a lazy cycle of pitch-classes in ascending order"
  (cycle pcs-ordered))

(def pcs-cycle-dn "a lazy cycle of pitch-classes in descending order"
  (drop 11 ; drop 11 to start on 0
        (cycle (reverse pcs-ordered))))

(defn pc-rotate "rotates pc in pitch-class-space by rot"
  [pc rot]
  (first (drop (Math/abs rot)
               (drop-while #(not (= pc %))
                           (if  (pos? rot)
                             pcs-cycle-up
                             pcs-cycle-dn)))))


; TODO: take an explicit length instead of using the final seq
(defn merge-seqs "merges the beats of each sequence, using the last sequence's length"
  [& seqs]
  (reduce (fn [acc sequence]
            (map-indexed (fn [i beat] (concat beat (nth sequence i []))) acc))
          (last seqs)
          (drop-last seqs)))

(defn rotate-seq "rotates sequence by n. given n=2 and s=[a b c d e] return [c d e a b]. given n=-2 return [d e a b c]"
  [n s]
  (map-indexed
   (fn [i beat] (nth s
                     (mod (+ i n) (count s))))
   s))

(defn map-if-num "Applies fn to each member of coll that is a number. Leaves non-numbers unchanged."
  [fn coll]
  (map #(if (number? %) (fn %) %)
       coll))

(defn map-if-not-empty "Applies fn to each member of coll that is not empty."
  [fn coll]
  (map #(if (empty? %) % (fn %))
       coll))

;; TODO: pair with vel-seq, cycle vel-seq
(defn with-vel "pairs notes with vel and dur. if note is not a number, it is left unchanged. vel defaults to 64"
  ([dur vel ns]
   (map-if-num (fn [n] [n vel dur]) ns))
  ([vel ns] (with-vel :1|4 vel ns))
  ([ns] (with-vel 64 ns)))

(defn chord "pairs notes with dur and vel and flattens. defaults to whole note (:1|1) and 64 if dur or vel are not supplied"
  ([dur vel ns] (flatten (with-vel dur vel ns)))
  ([vel ns] (chord :1|4 vel ns))
  ([ns] (chord 64 ns)))


(defn bin->rhy "Converts a seq of 0s and 1s to a seq of notes and []s"
  [n vel bin-seq]
  (->> bin-seq
       (map #(if (= 0 %) [] n))
       (with-vel vel)))

(defn fill "returns a coll of length n filled with v, leaving existing values untouched"
  ([n v coll] (take n (concat coll (repeat v))))
  ([n v] (fill n v []))
  ([n] (fill n [] [])))
