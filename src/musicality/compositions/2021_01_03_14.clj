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
   (s/send-beat beat sub-beat :note (c/chord coll))
   coll)
  ([coll beat] (send-chord coll beat 1))
  ([coll] (send-chord coll 1)))

(defn send-scale [coll]
  (->> coll
       (map-indexed (fn [i n] (s/send-beat (+ 1 i) :note [n 64 :1|4])))
       (doall))
  coll)

(defn rand-pc-set
  "returns a map containing 
  :pc-set - record from pc-sets
  :ints - result of applying pc-set to n0
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
    (:description pc-set)))

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

(defn map-pc-sets-over-ints
  "generates a chord progression based on a carrier scale
  of ints and seq of tuples of [1-based-root pc-set]"
  [carrier prog]
  (->> prog
       (c/map-if-not-empty (fn [[degree pc-set]]
                             (pc-set->ints pc-set
                                           (nth carrier (- degree 1)))))))





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



 ; nice hand-pan and beat
#_(let [pc-set 60
      pc-set-vec [1 3 6 7 10 11]
      root 1]
  (->> (c/merge-seqs 32

                                        ; bembe
                     (->> (E 7 16)
                          (rotate-seq 5)
                          (c/bin->rhy (pc-set->ints pc-set (to-octave root 2))
                                      [12 32]
                                      :1|4)
                          (shuffle)
                          (cycle))

                                        ; bembe 2
                    (->> (E 7 16)
                          (rotate-seq 7)
                          (c/bin->rhy (->> pc-set-vec
                                           (map #(to-octave % 5))
                                           (shuffle))
                                      [40 32 24]
                                      [:1|8 :1|16 :1|32])
                          (cycle))

                    (->> (E 13 16)
                          (rotate-seq 7)
                          (c/bin->rhy (->> pc-set-vec
                                           (map #(to-octave % 4))
                                           (shuffle))
                                      [40 32 24]
                                      [:1|8 :1|16 :1|32])
                          (cycle))

                                        ; kick
                     (->> (E 7 16)
                          (c/bin->rhy 6 12)
                          (cycle))

                                        ; hi-hat
                     (->> (E 6 16)
                          (rotate-seq 2)
                          (c/bin->rhy 10 30)
                         #_ (map-indexed (fn [i s]
                                         (->> s
                                              (c/rotate-seq (* 3 i))
                                              (c/bin->rhy 13
                                                          45
                                                          :1|64))))
                          (cycle))

                     ; fast hats
                      (->> (E 7 12)
                          (repeat 7)
                          (map-indexed (fn [i s]
                                         (->> s
                                             #_ (c/rotate-seq (* 4 i))
                                              #_(shuffle)
                                              (c/bin->rhy 10
                                                          [10 5 10 10]
                                                          :1|64))))
                          (shuffle)
                          (c/fill 32 [])
                          (c/rotate-seq 1))

                    #_ (->> (E 3 12)
                          (repeat 7)
                          (map-indexed (fn [i s]
                                         (->> s
                                              (c/rotate-seq (* 3 i))
                                              (c/bin->rhy (->> (+ (to-octave root 4) (* 12 (mod i 3)))
                                                               (pc-set->ints pc-set)
                                                               (reverse))
                                                          45
                                                          :1|64))))
                          (c/fill 48 [])
                          (c/rotate-seq 0))

                    #_ (->> (E 2 12)
                          (repeat 14)
                          (map-indexed (fn [i s]
                                         (->> s
                                              (c/rotate-seq (* 4 i))
                                              #_(shuffle)
                                              (c/bin->rhy (->> (+ (to-octave root 3) (* 12 (mod i 3)))
                                                               (pc-set->ints pc-set)
                                                               (reverse))
                                                          
                                                          :1|64))))
                          (shuffle)
                          (c/fill 32 [])
                          (c/rotate-seq 1)))

       (clear)
       (s/send-seq :note)))




(comment "play some random chords and scales"

         (deinit)

         ; play a scale
         (->> [0 2 4 5 7 9 11]
              (map #(+ % 60))
              (map (fn [n] [n 64 :1|8]))
              (s/send-seq :note))

         (clear)


         ; Change a cc over duration


         (send-beat 2 :cc [12 127 :1|8])
         (send-beat 7 :cc [12 0 :1|2])


         ; Change all the CCs over duration


         (->> (range 128)
              (map (fn [x] [x 127 :1|4]))
              (flatten)
              (send-beat 1 :cc))

         (->> (range 128)
              (map (fn [x] [x 0 :1|4]))
              (flatten)
              (send-beat 6 :cc))

         (clear)



         ; play a random chord with 4 notes with description including "fourth"


         (->> (p/find-pc-set 4 "fourth")
              (rand-nth)
              ((fn [pc-set]
                 (send-chord (pc-set->ints (:id pc-set) 0 60))
                 [(:id pc-set) (:description pc-set)])))

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
                                 (cycle))

                            (->> (E 5 12)
                                 (rotate-seq 1)
                                 (c/bin->rhy 1 [64 127] :1|64)
                                 (cycle))


                            ; fill some sub-beats


                            (->> (E 7 12)
                                 (repeat 3)
                                 (map-indexed (fn [i s] (c/rotate-seq (* -1 i) s)))
                                 (map #(c/bin->rhy [1 0 4] 10 :1|64 %)))

                            (->> (E 6 12)
                                 (rotate-seq 1)
                                 (c/bin->rhy [1 0 6] 64 :1|64)))

              (clear)
              (s/send-seq :note))

        

         (s/send-beat 1 :context (pc-set->ints 60 0))

; pc-set 90 MKII Bark
; pc-set 100 MKII Super Bark 150BPM
         (let [pc-set 5
               root 0]
           (->> (c/merge-seqs 96

                                        ; mid
                              (->> (E 13 24)

                                   (rotate-seq 3)
                                   (repeat 2)
                                   (flatten)

                                   (c/bin->rhy (pc-set->ints pc-set (to-octave root 4))
                                               [64 32]
                                               :1|16)
                                   (cycle))

                                        ; bass
                              (->> (E 7 12)
                                   (rotate-seq 6)
                                   (c/bin->rhy (pc-set->ints pc-set (to-octave root 2))
                                               [64 32]
                                               :1|8)
                                   (cycle))


                                        ; high


                              (->> (E 19 32)
                                   (rotate-seq 5)
                                   (c/bin->rhy (->> (pc-set->ints pc-set (to-octave root 5))
                                                    (repeat 5)
                                                    (map-indexed (fn [i s] (map
                                                                            (fn [n] (to-octave n (+ 3 i)))
                                                                            s)))
                                                    (flatten)
                                                    (reverse))
                                               [48 32 12]
                                               :1|32)
                                   #_(cycle))


                                        ; kick


                              (->> (E 4 12)
                                   (c/bin->rhy 6 32)
                                   (cycle))

                                        ; hi-hat
                              (->> (E 3 12)
                                   (rotate-seq 1)
                                   (c/bin->rhy 3 8)
                                   (cycle)))

                (clear)
                (s/send-seq :note))
           (get-pc-set pc-set))

; 117 3
; 25 0
         (let [pc-set 117
               root 0]
           (->> (c/merge-seqs 96

                                        ; mid
                              (->> (E 13 24)

                                   (rotate-seq 3)
                                   (c/bin->rhy (pc-set->ints pc-set (to-octave root 3))
                                               [32 20]
                                               :1|4)
                                   (repeat 3)
                                   (mapcat identity))

                                        ; bass


                              (->> (E 7 12)
                                   (rotate-seq 4)
                                   (c/bin->rhy (pc-set->ints pc-set (to-octave root 2))
                                               [22 34]
                                               :3|16)
                                   (cycle))


                                        ; high


                              (->> (E 19 32)
                                   (rotate-seq 5)
                                   (c/bin->rhy (->> (pc-set->ints pc-set (to-octave root 5))
                                                    (repeat 3)
                                                    (map-indexed (fn [i s] (map
                                                                            (fn [n] (to-octave n (+ 4 i)))
                                                                            s)))
                                                    (flatten)
                                                    (reverse))
                                               [5 10 12 2 5]
                                               :1|2)
                                   (cycle))


                                        ; kick


                              (->> (E 7 12)
                                   (c/bin->rhy 6 32)
                                   (cycle))

                                        ; hi-hat
                              (->> (E 5 24)
                                   (rotate-seq 1)
                                   (c/bin->rhy 3 8)
                                   (cycle)))

                (clear)
                (s/send-seq :note))
           (get-pc-set pc-set))








         ; jazz beat with some randomized accents on ride and snare


         (->> (c/merge-seqs 48
                            [[6 64 :1|4]]

                            ; kick
                            (->> (E 4 12)
                                 (c/bin->rhy 6 32)
                                 (cycle))

                            ; hi-hat
                            (->> (E 2 12)
                                 (rotate-seq 3)
                                 (c/bin->rhy 3 32)
                                 (cycle))

                            ; ride
                            (->> [1 0 0 1 0 1]
                                 (cycle)
                                 (take 48)
                                 (c/bin->rhy 10 (->> (range 24) (map (fn [n] (+ 20 (rand-int 32)))))))

                            ; snare
                            (->> (E 3 4)
                                 (rotate-seq 2)
                                 (cycle)
                                 (take 48)
                                 (c/bin->rhy 14 (->> (range 24) (map (fn [n] (+ 10 (rand-int 26)))))))

                            ; chord progression
                            (->> [[2 :min] [] [] []  [5 :maj] [] [5 :dom7] [] [1 :maj] [] [] []]
                                 (map-pc-sets-over-ints (pc-set->ints 276 60))
                                 (map c/chord)))

              (s/clear)
              (s/send-seq :note))

         (->> [[2 :min] [] [] []
               [5 :maj] [] [5 :dom7] []
               [1 :maj] [] [] []]
              (send-prog-pc-sets-over-ints (pc-set->ints 276 60))
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


         (map-pc-sets-over-ints
          (pc-set->ints 276 (+ 4 57))
          [[1 :maj] [2 :min] [4 :maj] [5 :maj]])

         (p/find-pc-set 4 "maj")

         (nth p/pc-sets  276)


         ; TODO: make one that uses degrees of the carrier chord-scale
         ; TODO: accept literals

         ; TODO: set carrier scale in state atom, schedule fns that play chords based on current state. send notes for immediate playback instead of scheduling (how much latency will this add?)


         (send-scale
          (pc-set->ints 276 60))

         (send-scale
          (concat (pc-set->ints 276 1 60) [72]))



;; Use this as your DJ for the weekend.
         )
