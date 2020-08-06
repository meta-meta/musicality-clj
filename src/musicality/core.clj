(ns musicality.core
  (:use [overtone.osc :only (osc-client osc-close osc-handle osc-rm-handler osc-send osc-server)])
  (:gen-class)) ; https://github.com/bbatsov/clojure-style-guide#prefer-require-over-use

;;;; Musicality Live-compose

;;; The idea is to distill musical ideas and rearrange them on the fly. To decompose and recompose. To refactor music.
;;; The notes on a staff are a publication. They are output. They are instructions, removed from the ideas behind the notes.


;;; Start OSC
;; TODO: musicality.live-compose has dep injection to send/receive OSC
;; TODO: could midi sync directly via https://github.com/overtone/midi-clj instead of using Max
#_(def client (osc-client "localhost" 8000))
#_(def client (osc-client "192.168.1.12" 8000))
(def server (osc-server 9001))
#_(osc-close server)
#_(osc-close client)

;;; Representing Pitch Classes

;; One approach would assign ૪ and Ɛ to 10 and 11. this would allow numbers to directly represent pitch-class.
;;#_(def ૪ 10) ; invalid number
#_(def Ɛ 11) ; this one is fine


;; Rather than try to solve this with a different char or something, use keywords.
(def pitch-classes #{:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ})
;; TODO (annoying that :0 is not highlighted with clojure-keyword-face)
;; https://stackoverflow.com/questions/39192226/create-a-keyword-from-a-number  - maybe not highlighting because it's in a grey area

;; We can order these, but to simply define them, I think it's more formal/precise to define as a set.
(def pitch-class-order [:0 :1 :2 :3 :4 :5 :6 :7 :8 :9 :૪ :Ɛ])

;; This gives us the order, but it is not isomorphic to the thing we're trying to represent. :0 should technically follow :Ɛ because the shape of the pitch-class-space makes it so. We could operate on the pitch-classes indirectly via mod12 int arithmetic. To do so, we'd have to convert pc->int and then int->pc

(def pc->int "returns int corresponding to pitch-class keyword"
  (zipmap pitch-class-order (range)))

#_(pc->int :Ɛ)
#_(map pc->int pitch-class-order)

(defn int->pc "returns pitch-class keyword corresponsing to int"
  [x] (nth pitch-class-order (mod x (count pitch-class-order)) x))

#_(map int->pc (range 24))

;; Rotate up a P5 or down P4 using modulo arithmetic
#_(map #(int->pc (+ 7 (pc->int %))) [:0 :4 :7 :Ɛ])
#_(map #(int->pc (+ -5 (pc->int %))) [:0 :4 :7 :Ɛ])

;; Or we could define an infinite cycle
(def pitch-class-cycle-up "a lazy cycle of pitch-classes in ascending order"
  (cycle pitch-class-order))
#_(take 36 pitch-class-cycle-up)

(def pitch-class-cycle-dn "a lazy cycle of pitch-classes in descending order"
  (drop 11 ; drop 11 to start on 0
        (cycle (reverse pitch-class-order))))
#_(take 36 pitch-class-cycle-dn)

(defn pitch-class-rotate "rotates x in pitch-class-space by rot"
  [x rot]
  (first (drop (Math/abs rot)
               (drop-while #(not (= x %))
                           (if  (pos? rot)
                             pitch-class-cycle-up
                             pitch-class-cycle-dn)))))
#_(map #(pitch-class-rotate % 7) [:0 :4 :7 :Ɛ])
#_(map #(pitch-class-rotate % -5) [:0 :4 :7 :Ɛ])

(defn pitch-class-rotate-all "rotates xs in pitch-class-space by rot"
  [xs rot] (map #(pitch-class-rotate %1 rot) xs))

#_(pitch-class-rotate-all [:0 :4 :7 :Ɛ] 7)
;; TODO: should this be legal? seems convenient.
#_(pitch-class-rotate-all [:0 :4 :7 :Ɛ] :7)

;; What about unordered?
#_(map #(pitch-class-rotate % 7) #{:0 :4 :7 :Ɛ})
;; Nope, it returns a list. To maintain a set, gotta reduce
#_(reduce (fn [a b] (conj a (pitch-class-rotate b 7))) #{} #{:0 :4 :7 :Ɛ})
;; TODO: handle this in pitch-class-rotate-all


(comment "Terms
* beat: a collection of events to be added to the measure.
* sub-beat: a collection of events at subdivisions of a beat
* event: could be a MIDI event or a fn (or an osc message to be sent to a max synthesizer?)
* measure: a collection of beats that is looped by the sequencer


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

(defn send-beat "sends a beat of data to the max sequencer. beat is 1-based; sub-beat is 1-based subdivision of beat; type is one of :fn :note :cc; data is pairs of note vel or fn fn-name"
  [beat sub-beat type data]
  (apply (partial osc-send client (str "/midiSeq/" (name type) "/" beat "/" sub-beat))
         (map #(if (number? %) (int %) %) data)))

#_(send-beat 1 1 :note [60 23 64 24 67 23 71 51])
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

(defn merge-seqs "merges the beats of each sequence, using the last sequence's length"
  [& seqs]
  (reduce (fn [acc sequence]
            (map-indexed (fn [i beat] (concat beat (nth sequence i []))) acc))
          (last seqs)
          seqs))

(defn rotate-seq "rotates sequence by n"
  [n s]
  (map-indexed
   (fn [i beat] (nth s
                     (mod (+ i n) (count s))))
   s))

#_(rotate-seq 1 [0 1 2 3])
#_(->> [0 1 2 3]
       (rotate-seq 1))

(defn repeat-flat [n coll] (flatten (repeat n coll)))

#_(repeat-flat 5 [1 2])
#_(->> [1 2]
       (repeat-flat 5))

(defn map-if-num [fn coll]
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

#_(send-beat 1 1 :note (chord [60 64 67] 30))
#_(send-beat 2 1 :note (chord [65 69 72] 20))
#_(send-seq (with-vel [[] [] [] [] 69 [] [] [] [] [] [] []]) :note)
#_(send-seq (with-vel [[] [] [] [] 69 (chord [72 75 79] 30) [] [] [] [] [] []]) :note)
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
                 [40 :r :r
                  40 :r 40
                  40 :r :r
                  40 :r 40]) ; jazz ride
               
               (with-vel 0
                 (rotate-seq 0
                             [47 :r 47
                              :r 47 47
                              :r 47 :r
                              47 :r 47])) ; bembe wheel

               (with-vel 0
                 (rotate-seq 0 [66 66 66 :r 66 66 66 :r 69 67 66 :r])) ; snare ostenato
               )

              (send-seq :note))


         (clear))

(comment "some test sequences"
         (def sequences {:song-1 [[60 50] [96 30] [76 50]
                                  [62 50] [90 50] [72 50]
                                  [64 50] [] [74 50]
                                  [66 50] [] ["fn" "song-2"]]

                         :song-2 [[67 50 50 40 47 50 71 30] [] []
                                  [64 50 33 40 49 50 71 30] [] []
                                  [63 50 30 40 51 50 73 30] [] []
                                  [62 50 40 40 63 50 74 30] [] [90 60 95 60 "fn" "song-1"]]

                         :song-3 (map (fn [n] [;; (+ 60 (* 2 n)) 65 
                                               (+ 64 (* 1 n)) 65
                                               (- 59 (* 4 n)) 65]) (range 12))

                         :song-4 [[60 50] [] [] [] [62 50] [64 50] [] [] [] [] [] [66 50]]

                                        ;              :song-5 [[] [] [] [90 10 101 5] [] [] [] [] [30 10] (chord [60 64 67 71]) [] ["fn" "song-6"]]
                                        ;             :song-6 [(chord [64 68 73]) (chord (map #(+ 2 %) [64 68 73])) [] [] [] [] [] [] [] [] [] ["fn" "song-7"]]
                                        ;            :song-7 [[69 50] [] [] [] [] [] [69 10] [] [] [] [] ["fn" "song-8"]]
                                        ;           :song-8 [(chord [56 59 63 67]) [] [] [] [] [] [59 10] [] [] [] [] ["fn" "song-5"]]

                                        ;              :song-5 [[60 40] [] [] [] [67 40] [] [] [] [65 30 58 10] [] [] [] (chord [67 71 75 79]) [] [] ["fn" "song-6"]]
                                        ;              :song-6 [[] [] [] [] [] [] [] [] (chord [46]) [] [] [] [] [] [] ["fn" "song-7"]]
                                        ;              :song-7 [(chord [60 65 68 71]) [] [] [] [] [] [] [] [] [] [] [] [] [] [] ["fn" "song-8"]]
                                        ;              :song-8 [(chord [55 67 70 74]) [] [] [] [] [] [] [] [] [] [] [] [] [] [] ["fn" "song-5"]]

                         :song-5 [[60 40] [] [] [] [67 40] [] [] [] [65 30 58 10] [] [] [] (chord [67 71 75 79]) [] [] ["fn" "song-6"]]
                         :song-6 [[60 40] [] [] [] [67 40] [] [] [] [65 30 58 10] [] [] [] (chord [67 71 75 79]) [] [] ["fn" "song-7"]]
                         :song-7 [(chord [60 65 68 71]) [] [] [] [] [] [] [] [] [] [] [] [] [] [] ["fn" "song-8"]]
                         :song-8 [(chord [55 67 70 74]) [] [] [] [] [] [] [] [] [] [] [] [] [] [] ["fn" "song-5"]]}))

(comment "how to use send-seq"

         (send-seq
          (with-vel (map #(+ 60 (* 2 %)) (range 12)) 20))

         (send-seq (with-vel [40 :r :r
                              40 :r 40
                              40 :r :r
                              40 :r 40]))

         (rotate-seq 2
                     (merge-seqs [[] [] [] []] [[60 10]]) ; TODO why does this return ((60 10 60 10)) ?
                     )

         (->> [[] [] [] []]
              (merge-seqs [[] [60 10] []]
                          [[] [] [70 13]])
              (rotate-seq 1))

         (send-seq
          (merge-seqs

           (with-vel (repeat 32 []))

           (with-vel (rotate-seq (map #(+ 32 (* 5 %)) (range 12)) 2) 1)

           (with-vel (repeat-flat 8 [30 :r :r]) 5) ; four on floor

           ;; (with-vel (repeat-flat 2 [:r :r :r 78 :r :r]) 10) ; 2 and 4

           (with-vel (map-if-num #(+ 38 %) [40 :r :r
                                            40 :r 40
                                            40 :r :r
                                            40 :r 40]) 10) ; jazz ride
           (with-vel
             (rotate-seq
              (repeat-flat 3 (map #(if (number? %) (+ 66 %) %)
                                  [0 :r 0
                                   :r 0 0
                                   :r 0 :r
                                   0 :r 0])) 6)
             5) ; bembe wheel

           (with-vel
             (rotate-seq (map-if-num
                          #(- % 20)
                          (identity [66 66 66 :r 56 60 66 :r 66 67 66 :r
                                     :r :r :r 54 :r :r :r :r 69 67 66 56])) 10)
             1)) ; snare ostenato
          )

; number 2
         (send-seq
          (merge-seqs

           (with-vel (rotate-seq (map #(+ 66 (* 4 %)) (range 12)) 2) 0)

           (with-vel (repeat-flat 4 [60 :r :r]) 0) ; four on floor

           (with-vel (repeat-flat 2 [:r :r :r 77 :r :r]) 0) ; 2 and 4

           (with-vel [40 :r :r
                      40 :r 40
                      40 :r :r
                      40 :r 40] 0) ; jazz ride
           (with-vel
             (rotate-seq
              [47 :r 47
               :r 47 47
               :r 47 :r
               47 :r 47] 0)
             0) ; bembe wheel

           (with-vel
             (rotate-seq [66 66 66 :r 66 66 66 :r 69 67 66 :r] 0)
             0)) ; snare ostenato
          )

         (send-beat 12
                    ["fn"
                     (str '#(send-beat 11 [90 20]))])

         ((eval (read-string (str '#(send-beat 11 [80 20])))))

         (send-seq (with-vel [40 :r :r
                              40 :r 40
                              40 :r :r
                              40 :r 40])))

;; TODO: atom
(def fns {:fn1 #()})

;; TODO: allow args
(defn handle-fn "executes fn parsed from osc-msg"
  [osc-msg]
  (let [msg (first (:args osc-msg))
        fn-keyword (keyword msg)]
    (if (contains? fns fn-keyword)
      (eval (fns fn-keyword))
      (do ; TODO figure out how to literal
;        ((eval (read-string msg)))
;        (println (eval (read-string msg)))
        ))
    nil))

(osc-handle server "/fn" #'handle-fn)

(dir musicality.core)



