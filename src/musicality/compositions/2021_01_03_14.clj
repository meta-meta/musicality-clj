(ns musicality.compositions.2021-01-03-14
  (:use [musicality.compose :as c]
        [musicality.pc-sets :as p]
        [musicality.schedule :as s]))

#_(s/init)
#_(s/init "192.168.1.12")
#_(s/deinit)

#_(s/send-beat 1 :note [67 64 :1|4])
#_(s/clear)


(defn send-chord
  ([coll beat sub-beat]
   (s/send-beat beat sub-beat :note (c/chord coll)))
  ([coll beat] (send-chord coll beat 1))
  ([coll] (send-chord coll 1)))

(defn send-scale [coll]
  (->> coll
       (map-indexed (fn [i n] (s/send-beat (+ 1 i) :note [n 64 :1|4])))
       (doall)))


(defn rand-pc-set
  "returns a map containing 
  :pc-set - record from pc-sets
  :ints - result of applying pc-set to 
  "
 [pc-count n0]
  (let [pc-set (->> (p/find-pc-set pc-count "")
                    (rand-nth))]
    {:pc-set pc-set
     :ints (->> pc-set
                (:prime-form)
                (map c/pc->int)
                (map #(+ n0 %)))}))

(defn play-random-chord [pc-count n0]
  (let [{pc-set :pc-set ints :ints} (rand-pc-set pc-count n0)]
    (send-chord ints)
    (:description pc-set)))



(defn play-random-scale [pc-count n0]
  (let [{pc-set :pc-set ints :ints} (rand-pc-set pc-count n0)]
    (send-scale ints)
    (:description pc-set))
  )

(defn pc? [x] (contains? c/pcs x))

(defn to-octave [x o]
  (let [n (mod (if (pc? x)
                 (c/pc->int x)
                 x)
               12)]
    (+ n (* 12 (+ 1 o)))))

(defn pc-set->ints
  "given either the index of a pc-set or a keyword of a common pc-set such as :maj, rotate the pc-set by rot.
  if a :common-rotation exists for the pc-set, initially rotate accordingly then apply rot"
  ([pc-set-id rot n0]
   (let [pc-set (if (keyword? pc-set-id)
                  (p/get-pc-set pc-set-id)
                  (nth p/pc-sets pc-set-id))
         {prime-form :prime-form
          common-rotation :common-rotation} pc-set
         rot-total (if (nil? common-rotation)
                     rot
                     (+ common-rotation rot))]

     (->> prime-form
          (c/rotate-seq rot-total)
          (map #(c/pc-rotate % (* -1
                                  (pc->int (nth prime-form (mod rot-total (count prime-form)))))))
          (map c/pc->int)
          (map #(+ n0 %)))))
  ([pc-set-id n0] (pc-set->ints pc-set-id 0 n0)))

(defn send-prog-pc-sets-over-ints
  "generates and sends a chord progression based on a carrier scale
  of ints and seq of tuples of [1-based-root pc-set]"
 [carrier prog]
  (clear)
  (->> prog
       (c/map-if-not-empty (fn [[degree pc-set]]
              (pc-set->ints pc-set
                            (nth carrier (- degree 1)))))
       #_(map-indexed (fn [i chord]
                      (when-not (empty? chord)
                        (send-chord chord (+ 1 i)))))))





;; https://gist.github.com/virtualtam/9d1df5fc6d38c6fc5c3d
(defn split-seq "Extract a tail of all same elements: [1 1 0 0 0] -> [[1 1] [0 0 0]]"
  [s]
  (let [l (last s)]
    (split-with #(not= l %) s)))

(defn recombine
  "Distribute tail: [[1] [1] [1] [0] [0]] -> [[1 0] [1 0] [1]]"
  ([a b] [a b])
  ([a b c] [a b c])
  ([a b c & more]
     (let [s (concat [a b c] more)
           [head tail] (split-seq s)
           recombined (map concat head tail)
           r-len (count recombined)]
       (if (empty? head)  ;; even pattern (all supbatterns same)
         s
         (apply recombine (concat
                           recombined
                           (drop r-len (drop-last r-len s))))))))

(defn E "Evenly distribute K beats among N subdivisions"
  [k n]
  (let [seed (concat (repeat k [1]) (repeat (- n k) [0]))]
    (flatten (apply recombine seed))))

























(comment "play some random chords and scales"

         (deinit)

         ; play a scale
         (->> [0 2 4 5 7 9 11]
              (map #(+ % 60))
              (map (fn [n] [n 64 :1|4]))
              (s/send-seq :note))

         (->>
          (p/find-pc-set 4 "fourth")
          #_(map #(:description %))
          (rand-nth)
          (fn [pc-set]
            #_(send-chord (pc-set->ints (:id pc-set) 0 60))
            (:description pc-set)))

         (play-random-chord 4 60)

         (->> [0 5 4]
              (map #(+ 60 %))
              (map (fn [n] (pc-set->ints 60 n)))
              (interpose [])
              (map-indexed (fn [i c] (send-chord c i))))

         (s/send-beat 1 :note [1 30 :1|1])

         
         ; Euclidean rhythms for beats and sub-beats
         (->> (c/merge-seqs 48

                            (->> (E 8 12)
                                 (c/bin->rhy 4 [64 32] :1|64)
                                 (cycle)
                                 )

                            (->> (E 5 12)
                                 (rotate-seq 1)
                                 (c/bin->rhy 1 [64 127] :1|64)
                                 (cycle)
                                 )
                                                       
                             
                            ; fill some sub-beats
                            (->> (E 7 12)
                                 (repeat 3)
                                 (map-indexed (fn [i s] (c/rotate-seq (* -1 i) s)))
                                 (map #(c/bin->rhy [1 0 4] 10 :1|64 %)))

                            (->> (E 6 12)
                                 (rotate-seq 1)
                                 (c/bin->rhy [1 0 6] 64 :1|64)
                               )
                            )
              
              (clear)
              (s/send-seq :note))


         ; jazz beat with some randomized accents on ride and snare
         (->> (c/merge-seqs 48
                            [[6 64 :1|4]]

                            ; kick
                            (->> (E 4 12)
                                 (c/bin->rhy 6 32)
                                 cycle)

                            ; hi-hat
                            (->> (E 2 12)
                                 (rotate-seq 3)
                                 (c/bin->rhy 3 32)
                                 cycle)

                            ; ride
                            (->> [1 0 0 1 0 1]
                                 (cycle)
                                 (take 48)
                                 (c/bin->rhy 10 (->> (range 24) (map (fn [n] (+ 20 (rand-int 32))))))
                                 
                                )

                            ; snare
                            (->> (E 3 4)
                                 (rotate-seq -2)
                                 (cycle)
                                 (take 48)
                                 (c/bin->rhy 14 (->> (range 24) (map (fn [n] (rand-int 32)))))

                                 )

                      )
              
              (clear)
              (s/send-seq :note))


         (->>
          (send-prog-pc-sets-over-ints
           (pc-set->ints 276 60)
           [[2 :min] [] [] []  [5 :maj] [] [5 :dom7] [] [1 :maj] [] [] []])
          (map c/chord)
          (s/send-seq :note))

         (->> (range 4)
              (map #(pc-set->ints :min7 % 60))
              (interpose [60])
              (s/clear)

              (c/fill 12 [])
              (c/rotate-seq -1)
              (map-indexed (fn [i c] (send-chord c (+ 1 i)))))

         (send-chord [] 2)
         (send-chord (pc-set->ints :min7 0 60))

         (clear)
         (play-random-chord 5 50)

         (play-random-scale 5 50)
         (play-random-scale 7 60)

         (pc-set->ints :maj7 2 60)

         (p/get-pc-set :maj7)


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
