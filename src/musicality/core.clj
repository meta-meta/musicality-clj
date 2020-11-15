(ns musicality.core
  (:use [overtone.osc :only (osc-client osc-close osc-handle osc-rm-handler osc-send osc-server)])
  (:gen-class)) ; https://github.com/bbatsov/clojure-style-guide#prefer-require-over-use

;;;; Musicality Live-compose

;;; The idea is to distill musical ideas and rearrange them on the fly. To decompose and recompose. To refactor music.
;;; The notes on a staff are a publication. They are output. They are instructions, removed from the ideas behind the notes.


;;; Start OSC
;; TODO: musicality.live-compose has dep injection to send/receive OSC
;; TODO: could midi sync directly via https://github.com/overtone/midi-clj instead of using Max
(def client (osc-client "localhost" 8000))
#_(def client qq(osc-client "192.168.1.12" 8000))

(def server (osc-server 9003))
#_(osc-close server)
#_(osc-close client)


;;; OSC Listeners
(def fns (atom {}))

(defn handle-fn "executes fn parsed from osc-msg"
  [osc-msg]
  (let [msg (first (:args osc-msg))
        fn-keyword (keyword msg)
        fns @fns]
    (if (contains? fns fn-keyword)
      ((fns fn-keyword))
      (println (str "couldn't find fn " fn-keyword)))
    nil))

(osc-handle server "/fn" #'handle-fn)

#_(swap! fns assoc :fn1 #(println "abacab"))
#_(send-beat 1 :fn "fn1")




;;; Representing Pitch Classes

;; One approach would assign ૪ and Ɛ to 10 and 11. this would allow numbers to directly represent pitch-class.
;;#_(def ૪ 10) ; invalid number
#_(def Ɛ 11) ; this one is fine


;; Rather than try to solve this with a different char or something, use keywords.
(def pcs "pitch-class keywords" #{:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ})
;; TODO (annoying that :0 is not highlighted with clojure-keyword-face)
;; https://stackoverflow.com/questions/39192226/create-a-keyword-from-a-number  - maybe not highlighting because it's in a grey area

;; We can order these, but to simply define them, I think it's more formal/precise to define as a set.
(def pcs-ordered "pitch-class keywords in order" [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ])

;; This gives us the order, but it is not isomorphic to the thing we're trying to represent. :0 should technically follow :Ɛ because the shape of the pitch-class-space makes it so. We could operate on the pitch-classes indirectly via mod12 int arithmetic. To do so, we'd have to convert pc->int and then int->pc

(def pc->int "returns int corresponding to pitch-class keyword"
  (zipmap pcs-ordered (range)))

#_(pc->int :Ɛ)
#_(map pc->int pitch-class-order)

(defn int->pc "returns pitch-class keyword corresponsing to int"
  [x] (nth pcs-ordered (mod x (count pcs-ordered)) x))

#_(map int->pc (range 24))

;; Rotate up a P5 or down P4 using modulo arithmetic
#_(map #(int->pc (+ 7 (pc->int %))) [:0 :4 :7 :Ɛ])
#_(map #(int->pc (+ -5 (pc->int %))) [:0 :4 :7 :Ɛ])

;; Or we could define an infinite cycle
(def pcs-cycle-up "a lazy cycle of pitch-classes in ascending order"
  (cycle pcs-ordered))
#_(take 36 pitch-class-cycle-up)

(def pcs-cycle-dn "a lazy cycle of pitch-classes in descending order"
  (drop 11 ; drop 11 to start on 0
        (cycle (reverse pcs-ordered))))
#_(take 36 pitch-class-cycle-dn)

(defn pc-rotate "rotates x in pitch-class-space by rot"
  [x rot]
  (first (drop (Math/abs rot)
               (drop-while #(not (= x %))
                           (if  (pos? rot)
                             pcs-cycle-up
                             pcs-cycle-dn)))))
#_(map #(pitch-class-rotate % 7) [:0 :4 :7 :Ɛ])
#_(map #(pitch-class-rotate % -5) [:0 :4 :7 :Ɛ])

; TODO: is this necessary? pretty basic map
(defn pcs-rotate "rotates xs in pitch-class-space by rot"
  [xs rot] (map #(pc-rotate %1 rot) xs))

#_(pitch-class-rotate-all [:0 :4 :7 :Ɛ] 7)
;; TODO: should this be legal? seems convenient.
#_(pitch-class-rotate-all [:0 :4 :7 :Ɛ] :7)

;; What about unordered?
#_(map #(pitch-class-rotate % 7) #{:0 :4 :7 :Ɛ})
;; Nope, it returns a list. To maintain a set, gotta reduce
#_(reduce (fn [a b] (conj a (pitch-class-rotate b 7))) #{} #{:0 :4 :7 :Ɛ})
;; TODO: handle this in pitch-class-rotate-all


(comment "Terms
* beat: a collection of events scheduled to fire at some subdivision of a measure.
* sub-beat: a collection of events scheduled to fire at some subdivision of a beat
* event: could be a MIDI event or a fn (or an osc message to be sent to a max synthesizer?)
* measure: a collection of beats (and sub-beats) that is looped by the sequencer


TODO: beat vs pulse vs tick vs meter vs measure

"

         ;; A pitch-class-set has no order. It should be treated thusly. It doesn't make sense for it to be added to a beat or sequence or anything other than another pc-set. It is abstract. It needs something to draw out actual pitches out from it. It needs something to give it order if used for a melody.
         (def some-pc-set  #{:0 :4 :7 :Ɛ})

         ;; A pitch-class-vector has order but no pitches.
         (def some-pc-vec [:0 :4 :7 :Ɛ])

         ;; There are all kinds of ways to turn pitch-class collections into pitch things that can be played like chords and melodies. Using normal form calculation could be helpful in choosing chord voicings. Given a pc-set, you want to be able to talk about the chord voicings by assigning some role to each member of the set. For a triad, you'll have a root, a third and a fifth. It might be unambiguous to calculate which is which depending on the spacing between pcs. Normal form should provide a general solution.

         ;; once roles are sorted out, voicings can be generated by talking about how each role should be represented. see forte numbers: https://en.wikipedia.org/wiki/List_of_pitch-class_sets -- is there any language built around these? using the index of the normal form, the triads work out to root: 0 third: 1 fifth: 2. yes, seems unambiguous to use the normal-form-index to refer to roles.

         ;; a voicing mask could be defined to place roles in octaves
         (defn pc-set->chord "applies a voicing-mask to pc-set to generate a set of pitches"
           [pc-set voicing-mask] #{})

         ;; given #{:0 :4 :7}
         ;; normal-form [0 4 7]    
         (pc-set->chord some-pc-set
                        {:root-octave 4                      ; :0 -> 60
                         0 #{0 -1 1}                         ; 0 refers to 0th index of normal-form: 0 -> 60 48 72
                         1 #{0}                              ; 1 = index 1 of normal-form: 4 ->  64
                         2 #{0 -1}                           ; 2 = index 2 of normal-form: 7 -> 67 49
                         })
         ;; we get the pich-set #{48 49 60 64 67 72} 

         ;; maybe cleaner:
         {0 #{3 4 5}                         ; 0 refers to 0th index of normal-form: 0 -> 60 48 72
          1 #{4}                             ; 1 = index 1 of normal-form: 4 -> 64
          2 #{3 4}                           ; 2 = index 2 of normal-form: 7 -> 67 49
          }


         ;; given  #{:0 :4 :7 :Ɛ}
         ;; normal form [0 1 5 8]


         [60 64 67 71] ; start with note vector 


         (let [some-notes  [60 64 67]
               as-chord (chord some-notes) ; 
               ])

         (+ 00 01 10) ; this can be nice

         ;; TODO instead of vectors
         (def some-measure [{:fn {2 {:fn1 ["arg1" "arg2" "arg3"]}}
                             :note {0 [60 127 65 120] 10 [20 55]}
                             :cc {0 [1 65] 1 [1 79] 2 [1 0]}}
                            {}
                            {}
                            {}]))


;;; Scheduling - TODO: break into separate ns?

(defn send-beat "sends a beat of data to the max sequencer. beat is 1-based; sub-beat is 1-based subdivision of beat; type is one of :fn :note :cc; data is [note vel] or [cc val] or fn-name"
  ([beat sub-beat type data]
   (apply (partial osc-send client (str "/midiSeq/" (name type) "/" beat "/" sub-beat))
          (if (= type :fn)
            [(name data)]               ;data is a keyword referring to a fn in fns map
            (map #(if (number? %) (int %) %) data))))
  ([beat type data]
   (send-beat beat 1 type data)))


#_(send-beat 1 :fn :fn1)
#_(send-beat 1 1 :note [60 23 64 24 67 23 71 51])
#_(send-beat 1 :note [60 23 64 24 67 23 71 51])
#_(send-beat 1 1 :cc [46 127])

;; TODO: figure out how to send args. serialize?
#_(send-beat 2 1 :fn ["my-fn" "arg1" 2 3])

#_(doall
   (->> [1 5 9]
        (map #(send-beat 1 % :note [60 60]))))

#_(doseq [n [1 5 9]]
    (send-beat 1 n :note [(+ 60 n) 60]))

(defn clear "clears all data in all beats"
  [] (osc-send client "/midiSeq/clear"))

#_(clear)
#_(dir musicality.core) ;TODO  why doesn't this show anything?

;; TODO: rename
;; TODO: pair with vel-seq, cycle vel-seq
(defn with-vel "pairs notes with vel. if note is not a number, it is replaced with empty []. vel defaults to 64"
  ([vel ns]
   (map (fn [n] (if (number? n) [n vel] [])) ns))
  ([ns] (with-vel 64 ns)))

#_(with-vel 64 [0 1 2])
#_(->> [0 1 2]
       (with-vel 64))

;; TODO: rename
(defn chord "pairs notes with vel and flattens"
  ([vel ns] (flatten (with-vel vel ns)))
  ([ns] (chord 64 ns)))

#_(chord 62 [0 1 2])
#_(->> [60 64 67 71]
       (chord 22))

; TODO: take an explicit length instead of using the final seq
(defn merge-seqs "merges the beats of each sequence, using the last sequence's length"
  [& seqs]
  (reduce (fn [acc sequence]
            (map-indexed (fn [i beat] (concat beat (nth sequence i []))) acc))
          (last seqs)
          (drop-last seqs)))

(defn rotate-seq "rotates sequence by n"
  [n s]
  (map-indexed
   (fn [i beat] (nth s
                     (mod (+ i n) (count s))))
   s))

#_(rotate-seq 1 [0 1 2 3])
#_(->> [0 1 2 3]
       (rotate-seq 1))

;; TODO prob not necessary. use take and cycle
(defn repeat-flat [n coll] (flatten (repeat n coll)))

#_(repeat-flat 5 [1 2])
#_(take 10 (cycle [1 2]))
#_(->> [1 2]
       (repeat-flat 5))
#_(->> [1 2]
     (cycle)
     (take 10))

(defn map-if-num "Applies fn to each member of coll that is a number. Leaves non-numbers unchanged."
  [fn coll]
  (map #(if (number? %) (fn %) %)
       coll))

#_(map-if-num #(+ % %) [0 1 2 :r 3 [] 4])
#_(->> [0 3 [] :r 4 4]
       (map-if-num #(* % 2)))

(def sequences (atom {}))


;; TODO should beats have types merged or as separate sequences?
;; TODO send sub-beats in send-beat


(defn send-seq "sends a sequence of beats. if s is a keyword, lookup in sequences"
  [type s]

  (doseq [[beat data] (map-indexed
                       (fn [idx data] [(+ 1 idx) data])
                       (if (keyword? s)
                         (@sequences s)
                         s))]
    (when-not (empty? data)
      (send-beat beat 1 type data))))

#_(send-beat 1 1 :note (chord 30 [60 64 67]))
#_(send-beat 2 1 :note (chord 20 [65 69 72]))
#_(send-seq :note (with-vel [[] [] [] [] 69 [] [] [] [] [] [] []]))
#_(send-seq :note [[] [] [] [] [69 30] (chord 20 [72 77 80]) [] [] [] [] [] []])
#_(clear)

(comment "some new sequences 2020-07-24"

         (->> (merge-seqs

               (->> [0 4 6 :r :r 6 10 12 :r :r :r 15 10 5]
                    (map-if-num #(+ 31 %))
                    (with-vel 30))

               (->> [0 1 2 [] 1 0 [] [] -2 [] -2 -2]
                    (map-if-num (fn [t] (chord 10 (->> [0 4 7 11] (map #(+ 60 t %))))))))

              (send-seq :note))

         (clear)

         (defn chord-mask)

         (->> (merge-seqs
               (let [c1 (->> [0 4 7 11 14] (map #(+ % 42)) (chord 20))
                     c2 (->> [0 4 7 10 16 -5] (map #(+ % 58)) (chord 15))]
                 [c1 [] [] [] [] [] [] []
                  [] c1 [] [] [] [] [] []
                  c2 [] [] [] [] [] [] []
                  [] c1 [] [] [] [] [] []])

               (->> [[] 0 2 3 0 7 0 12 -10 -10 17 [] [] [] 22 [] [] 27]
                    (map-if-num #(+ 60 %))
                    (with-vel 25))

               #_(->> [0 :r 0
                       :r 0 0
                       :r 0 :r
                       0 :r 0]
                      (repeat-flat 4)
                      (map-if-num (fn [n] (+ 29)))
                      (with-vel 10)
                      (rotate-seq 1))

               (->> [0 :r 0
                     :r 0 0
                     :r 0 :r
                     0 :r 0]
                    (repeat-flat 2)
                    (map-if-num (fn [n] (+ 21)))
                    (with-vel 10)
                    (rotate-seq 5))

               (->> [0 :r 0
                     :r 0 0
                     :r 0 :r
                     0 :r 0]
                    (repeat-flat 1)
                    (map-if-num (fn [n] (+ 27)))
                    (with-vel 10)
                    (rotate-seq 6))

               (->> (range 32) (map (fn [n] [22 10]))))
              (send-seq :note))
         (clear)



         ;; TODO: send sub-beats

         (def jazz-ride [0 :r :r 0 :r 0])

         (->> (merge-seqs
               (->> (cycle [:r :r 0 :r :r :r 0 :r])
                    (take 32)
                    (map-if-num #(+ % 62))
                    (with-vel 10))

               (->> (cycle [0 0 :r :r])
                    (take 32)
                    (map-if-num #(+ % 55))
                    (rotate-seq 0)
                    (with-vel 10))

               

               (repeat 32 [])
               )
              (send-seq :note))


         



         (->> (merge-seqs
               
               (with-vel 10
                 [6 :r :r
                  6 :r 6
                  6 :r :r
                  6 :r 6]) ; jazz ride
               
               (with-vel 10
                 (rotate-seq 0
                             [02 :r 02
                              :r 02 02
                              :r 02 :r
                              02 :r 02])) ; bembe wheel

               (with-vel 10 (take 12 (cycle [0 :r :r])))

              (with-vel 4
                 (rotate-seq 0 [1 1 1 :r 1 1 1 :r 1 1 1 :r])) ; snare ostenato
               )

              (send-seq :note))


         (clear)


         (let [a (->> (merge-seqs

                       (->> [[:Ɛ :3 :6] [] [] [] [] []
                             [:Ɛ :5 :6] [] [] [] [] []
                             [:1 :6 :8] [] [] [] [] []
                             [:3 :5 :6] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 60) c)))
                            (map #(chord 20 %))
                            )

                       (->> [[] [:૪] [:1] [:8] [] [:5]
                             [] [:1] [] [] [] []
                             [] [:1] [:૪] [:3] [] [:1]
                             [] [:૪] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 72) c)))
                            (map #(chord 20 %))
                            )
                       
                       )
                      )
               b (->> (merge-seqs

                       (->> [[:Ɛ :3 :6] [] [] [] [] []
                             [:1 :3 :6] [] [] [] [] []
                             [:3 :5 :6] [] [] [] [] []
                             [] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 60) c)))
                            (map #(chord 20 %))
                            )

                       (->> [[:8] [] [] [] [] []
                             [] [] [:3] [] [:૪] []
                             [:8] [] [] [] [] []
                             [:1] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 72) c)))
                            (map #(chord 20 %))
                            )
                       
                       )
                      )

               c1 (->> (merge-seqs

                       (->> [[:Ɛ :6] [] [] [] [] []
                             [:1 :3] [] [] [] [] []
                             [:૪ :3] [] [] [] [] []
                             [:1 :6] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 60) c)))
                            (map #(chord 20 %))
                            )

                       (->> [[] [] [] [] [] []
                             [] [] [] [] [] []
                             [] [] [] [] [] []
                             [] [] [:1] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 72) c)))
                            (map #(chord 20 %))
                            )
                       
                       )
                      )
               c2 (->> (merge-seqs

                       (->> [[:Ɛ :6] [] [] [] [] []
                             [:1 :3] [] [] [] [] []
                             [:3 :7 :૪] [] [] [] [] []
                             [] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 60) c)))
                            (map #(chord 20 %))
                            )

                       (->> [[] [] [] [] [] []
                             [] [] [] [] [] []
                             [] [] [] [] [] []
                             [] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 72) c)))
                            (map #(chord 20 %))
                            )
                       
                       )
                      )
               d (->> (merge-seqs

                       (->> [[:Ɛ :8 :4] [] [] [] [] []
                             [] [] [] [] [] []
                             [:3 :7 :૪] [] [] [] [] []
                             [] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 60) c)))
                            (map #(chord 20 %))
                            )

                       (->> [[:3] [:1] [:3] [:1] [:3] []
                             [:3] [] [:3] [:1] [:3] [:Ɛ]
                             [:0] [:2] [:3] [:5] [:7] [:9]
                             [:૪] [:2] [:7] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 72) c)))
                            (map #(chord 20 %))
                            )
                       
                       )
                      )
               e (->> (merge-seqs

                       (->> [[:6 :8 :૪] [] [] [] [] []
                             [:5 :8 :૪] [] [] [] [] []
                             [:3 :8 :૪] [] [] [] [] []
                             [:3 :7 :૪] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 60) c)))
                            (map #(chord 20 %))
                            )

                       (->> [[:7] [:3] [] [:૪] [] [:6]
                             [:7] [] [] [:3] [] []
                             [:3] [] [] [] [] []
                             [] [] [] [] [] []]
                            (map (fn [c] (map #(+ (pc->int %) 72) c)))
                            (map #(chord 20 %))
                            )
                       
                       )
                      )

               
               seq [a a b c1 b c2 d e a c2]
               ]

           
           (swap! fns assoc :fn1-state 0)

           (swap! fns assoc :fn1 #(do
                                    
                                    (clear)
                                    (let [idx (:fn1-state @fns)]
                                      (if (= idx (count seq))
                                        (clear)
                                        (do
                                          (send-seq :note (nth seq (:fn1-state @fns)))
                                          (swap! fns assoc :fn1-state
                                                 (mod (+ 1 (:fn1-state @fns))
                                                      (count seq)))
                                          (send-beat 24 1 :fn :fn1)
                                          )))
                                    
                                    ))           
           )

         ((:fn1 @fns))

         (clear)

         ;; alternate between two seqs by scheduling fns
         (let [seqA (->> (merge-seqs
        
                          (with-vel 10
                            [6 :r :r
                             6 :r 6
                             6 :r :r
                             6 :r 6]) ; jazz ride
        
        
                          (with-vel 10 (take 12 (cycle [0 :r :r])))
        
                          (with-vel 4
                            (rotate-seq 0 [1 1 1 :r 1 1 1 :r 1 1 1 :r])) ; snare ostenato
                          ))
        
               seqB (->> (merge-seqs
        
                          (with-vel 10
                            (rotate-seq 0
                                        [02 :r 02
                                         :r 02 02
                                         :r 02 :r
                                         02 :r 02])) ; bembe wheel
        
                          (with-vel 10 (take 12 (cycle [0 :r :r])))
        
                          (with-vel 4
                            (rotate-seq 0 [1 1 1 :r 1 1 1 :r 1 1 1 :r])) ; snare ostenato
                          ))]
        
           (swap! fns assoc :fn1 #(do
                                    (clear)
                                    (send-seq :note seqB)
                                    (send-beat 12 1 :fn :fn2)))
           (swap! fns assoc :fn2 #(do
                                    (clear)
                                    (send-seq :note seqA)
                                    (send-beat 12 1 :fn :fn1)))
        
           ((:fn1 @fns)))

         (defn bin->rhy [n vel bin-seq]
           (->> bin-seq
                (map #(if (= 0 %) [] n))
                (with-vel vel)))

         (clear)
         (send-seq :note 
                   (merge-seqs
                    
                    (->> [0 1 1 1 0 0 1 1 1 0 0 0]
                         (repeat-flat 4) ; TODO: repeat to fill length n 
                         (bin->rhy 2 60))
                    
                    (->> [1 0 0 0 0 1 0 0 0 0 0 0]
                         (repeat-flat 8)
                         (bin->rhy 0 60))
                    ))


         (clear)
         (send-seq (merge-seqs
                    
                    (->> [0 1 1 1 0 0 1 1 1 0 0 0]
                         (repeat-flat 4) ; TODO: repeat to fill length n 
                         (bin->rhy 2 60))
                    
                    (->> [1 0 0 0 0 1 0 0 0 0 0 0]
                         (repeat-flat 8)
                         (bin->rhy 0 60))
                    )) ; play at 64/16, 16BPI

;         [013568૪7_7_6767_7__]
;

         )







(dir musicality.core)



