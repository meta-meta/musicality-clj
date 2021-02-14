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


(defn fill "returns a coll of length n filled with v, leaving existing values untouched"
  ([n v coll] (take n (concat coll (repeat v))))
  ([n v] (fill n v []))
  ([n] (fill n [] [])))


(defn merge-seqs "merges the beats and sub-beats of each sequence, using the length of the longest sequence or max-len.
  seqs can be lazy/infinite but sub-beats cannot (yet?)"
  [max-len & seqs]
  (->> seqs
       (reduce
        (fn [acc sequence]
          (->> acc
               (map-indexed (fn [i beat]
                              (let [sub-beats? (coll? (first beat))
                                    incoming-beat (nth sequence i [])
                                    sub-beats-incoming? (coll? (first incoming-beat))
                                    merge-sub-beats (fn [a b] (reduce
                                                               (fn [acc sub-beat]
                                                                 (->> acc
                                                                      (map-indexed (fn [i sb] (concat sb (nth sub-beat i []))))))
                                                               (fill (apply max (map count [a b])) [])
                                                               [a b]))]
                                (if (or sub-beats? sub-beats-incoming?)
                                  (merge-sub-beats
                                   (if sub-beats? beat [beat])
                                   (if sub-beats-incoming? incoming-beat [incoming-beat]))
                                  (concat beat incoming-beat)))))))
        (fill (apply max (map #(count (take max-len %)) seqs)) []) ; start with seq filled with lenth = largest seq or max-len []s
        )))


#_(merge-seqs 12 
          [[[] []] [1 64 :1|1] []]
          [[[] [2 30 :1|2 4 60 :1|4]] [] []])

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

(defn map-if-one "Applies fn to each member of coll that == 1"
  [fn coll]
  (map #(if (== 1 %) (fn %) %)
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


(defn as-coll "returns val-or-coll if coll, or [val-or-coll] if not"
  [val-or-coll] (if (coll? val-or-coll)
                  val-or-coll
                  [val-or-coll]))

; TODO: allow lazy-seqs for notes, vels, durs
(defn bin->rhy "Converts a seq of 0s and 1s to seq of notes and []s. Replaces 1s with successive notes in n-or-ns, likewise for vs and ds"
  ([note-or-notes vel-or-vels dur-or-durs bin-seq]
   (let [indexed-ones (->> bin-seq (filter #(== 1)))
         zeros-and-indices  (->> indexed-ones
                                 (map-indexed (fn [i n] (if (== n 1) i 0))))
         note-cycle (cycle (as-coll note-or-notes))
         vel-cycle (cycle (as-coll vel-or-vels))
         dur-cycle (cycle (as-coll dur-or-durs))
         mk-note (fn [i] [(nth note-cycle i) (nth vel-cycle i) (nth dur-cycle i)])]
     (->> bin-seq
          (reduce (fn [[next-index vec] val] (if (== 0 val)
                                               [next-index (conj vec [])]
                                               [(+ 1 next-index) (conj vec (mk-note next-index))]))
                  [0 []])
          (take-last 1)
          (first))))
  ([n-or-ns v-or-vs bin-seq] (bin->rhy n-or-ns v-or-vs :1|4 bin-seq))
  ([n-or-ns bin-seq] (bin->rhy n-or-ns 64 :1|4 bin-seq)))

#_(bin->rhy [6 9] 32 :1|2 [0 0 1 0 1 0 1 1 1 0])


