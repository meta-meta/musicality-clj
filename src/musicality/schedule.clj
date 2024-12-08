(ns musicality.schedule
  (:require [musicality.osc :as o]
           [musicality.symbols :refer :all])
  (:gen-class))


(def dur->max-note-val 
  "a map of duration keyword to Max note value. It would be nice to use ratios but I want this to work in cljs.
  :1|2 I find more readable than :1:2"

  {
   :3|2 "1nd"
   :1|1 "1n"
   :2|3 "1nt"
  
   :3|4 "2nd"
   :1|2 "2n"
   :1|3 "2nt"

   :3|8 "4nd"
   :1|4 "4n"
   :1|6 "4nt"

   :3|16 "8nd"
   :1|8 "8n"
   :1|12 "8nt"

   :3|32 "16nd"
   :1|16 "16n"
   :1|24 "16nt"

   :3|64 "32nd"
   :1|32 "32n"
   :1|48 "32nt"

   :3|128 "64nd"
   :1|64 "64n"
   ; there is no 64th note triplet

   :1|128 "128n"
   
})

(defn send-beat-count
  "Sends the number of pulses in a loop"
  [instr pulse-count]
  (o/send-osc (str "/" instr "/pulseCount") (int pulse-count)))

#_(send-beat-count 420)

(defn send-beat
  "Sends a 'beat' of data to the sequencer. 
  instr is a string corresponding to osc address of instrument
  beat is 1-based.  
  type can be :note :cc :fn.

  type -> data
  :note -> [note vel dur]
  :cc -> [cc target-val dur]
  :context -> [note note note ...] or \"clear\" (TODO: this will get richer)
  :fn -> fn-name"
  [instr beat type data]
  (let [addr (cond
               (= type :cc)
               (str "/" instr "/" (name type) "/" beat "/" (first data))

               (= type :oscMsg)
               (str "/" instr "/" (name type) "/msg/" beat (first data)) ; first member of data is the osc address to be scheduled
               
               :else
               (str "/" instr "/" (name type) "/" beat))
        msg (cond 
              (= type :fn)
              [(name data)] ; fn-name

              (= type :cc)
              (->> data
                   (rest)
                   (map float))

              (= type :oscMsg)
              (->> data
                   (rest)
                   (map #(if (number? %) (float %) %)))
              

              :else
              (->> data
                   (vec) ; data might be a #{}
                   (flatten)
                   (map #(cond
                           (number? %) (int %)
                           (keyword? %) (dur->max-note-val %)
                           :else %)
                        )))]
    (apply o/send-osc addr msg)))


(def ^:private fns "A map of fn keywords to definitions." (atom {}))

(defn- handle-fn "Executes fn parsed from osc-msg."
  [osc-msg]
  (let [msg (first (:args osc-msg))
        fn-keyword (keyword msg)
        fns @fns]
    (if (contains? fns fn-keyword)
      ((fns fn-keyword))
      (println (str "couldn't find fn " fn-keyword)))
    nil))

(defn send-fn
  "Sends a fn to be executed at beat"
  [instr beat fn-keyword fn]
  (swap! fns assoc fn-keyword fn)
  (send-beat instr beat :fn fn-keyword))


(defn send-beats "Sends a hash-map of beats.
  Each beat is a map that can contain :note :cc :fn :context data.
  So s might look like {1 {:note [60 64 :1|4, 65 64 :1|4]}, 4 {:fn :some-fn #()}}"
  [instr beats]
  (doseq [[beat mixed-data] beats]
    #_(prn beat mixed-data)
    (doseq [[type data] mixed-data]
      (prn instr beat type data)
      (if (= :fn type)
        (apply send-fn instr beat data)
        (send-beat instr beat type data)))))



#_(clear "pianoteq")
#_(send-beat-count "pianoteq" 420)
#_(send-beats "pianoteq"
              {1 {:note #{[60 32 :1|1] [66 44 :1|1]} :fn [:my-fn #(+ 1 1)]}
               4 {:context [0 2 4 6 8 10]}
               100 {:context ["clear"]
                    :note #{[65 64 :1|2]}}})

#_(clear "drums")
#_(send-beats "drums"
              {1 {:note #{[0 64 :1|1]}}})



(defn clear "clears all data in all beats. passes through a single argument for convenience"
  [instr] (o/send-osc (str "/" instr "/clear")))

(defn init "Creates/opens client and server and registers listeners" 
  ([ip]
   (if (nil? ip)
     (o/connect-local)
     (o/connect ip))
   (o/handle "/fn" #'handle-fn))
  ([] (init nil)))

(defn deinit "Closes client and server" []
  (o/disconnect))


(comment "usage"

         ; init first, make sure to deinit before cider-quit
         (init)
         (deinit)

         (clear "drums")
         (clear "pianoteq")

         ; fns are stored in an atom.
         @fns

         ; register a fn then schedule it
         (swap! fns assoc :fn1 #(println "fn1 executed"))
         (send-beat 1 :fn :fn1)
         (send-beat 3 :fn :fn1)

         ; or register and schedule in one shot.
         (send-fn 4 :fn2 #(println "four"))

         (send-beat 1 :note [70 60 :1|4])
         (send-beat 1 :note [60 23 :1|4, 64 24 :1|8, 67 23 :1|16, 71 51 :1|4])
         (send-beat 1 :note [60 64 66 60])

         (def dur :1|8)

         ; a 4 chord waltz         
         (send-seq :note
                   [[62 64 dur, 65 64 dur, 69 64 dur] [50 64 dur] [50 64 dur]
                    [67 64 dur, 71 64 dur, 74 64 dur] [55 64 dur] [55 64 dur]
                    [60 64 dur, 64 64 dur, 67 64 dur] [48 64 dur] [48 64 dur]
                    [64 64 dur, 67 64 dur, 71 64 dur] [52 64 dur] [52 64 dur]])


         
         
         

         



         ; same thing but with flourish in sub-beats
         (send-seq :note
                   [[62 64 dur, 65 64 dur, 69 64 dur] [50 64 dur] [50 64 dur]

                    [67 64 dur, 71 64 dur, 74 64 dur]
                    [[55 64 dur] [] [] [] [] []
                     [57 64 dur] [] [] [] [] [] []]
                    [[55 64 dur] [] [] [] [] []
                     [57 64 dur] [] [] [] [] []]

                    [60 64 dur, 64 64 dur, 67 64 dur] [48 64 dur] [48 64 dur]
                    [64 64 dur, 67 64 dur, 71 64 dur] [52 64 dur] [52 64 dur]])


         (send-fn 1 :f1 (fn [] (println "yo")  (send-seq :note [[60 30 :1|1]])))



         ; alternate between the two by sending fns that schedule each other
         (do
           (send-fn 12 12 :w1
                    (fn []
                      (clear)
                      (send-beat 1 :fn :w2)
                      (send-seq :note
                                [[62 64 dur, 65 64 dur, 69 64 dur] [50 64 dur] [50 64 dur]
                                 [67 64 dur, 71 64 dur, 74 64 dur] [55 64 dur] [55 64 dur]
                                 [60 64 dur, 64 64 dur, 67 64 dur] [48 64 dur] [48 64 dur]
                                 [64 64 dur, 67 64 dur, 71 64 dur] [52 64 dur] [52 64 dur]])
                      ))

           (send-fn 12 12 :w2
                    (fn []
                      (clear)
                      (send-beat 1 :fn :w1)
                      (send-seq :note
                                [[62 64 dur, 65 64 dur, 69 64 dur] [50 64 dur] [50 64 dur]
                                 
                                 [67 64 dur, 71 64 dur, 74 64 dur]
                                 [[55 64 dur] [] [] [] [] []
                                  [57 64 dur] [] [] [] [] [] []]
                                 [[55 64 dur] [] [] [] [] []
                                  [57 64 dur] [] [] [] [] []]
                                 
                                 [60 64 dur, 64 64 dur, 67 64 dur] [48 64 dur] [48 64 dur]
                                 [64 64 dur, 67 64 dur, 71 64 dur] [52 64 dur] [52 64 dur]])
                      ))
           
           )


         ; is there any benefit to coming up with one canonical data structure for an arrangement?
         [[{:n [62 65 69] :v 64}] [] []
          [] [] []
          [] [] []
          [] [] []]


         ; TODO: this is only so useful. It should probably be instructions on how to change cc over time
         #_(send-beat 1 :cc [46 0 127 4]) ; at beat 1, cc 46 starts at 0 and goes to 127 over the span of 4 beats.
         (send-beat 1 1 :cc [46 127])


         ;; TODO: figure out how to send args. serialize?


         (send-beat 2 1 :fn ["my-fn" "arg1" 2 3])

         (doseq [n [1 5 9]]
           (send-beat 1 n :note [(+ 60 n) 60]))



         (* 12 ; beats
            12 ; MIDI-beats (24 per quarter note, 12 per eight)
            8  ; measures
            )


         (let [bpi 32
               measure-count 8
               beats-per-measure 12
               note-value-per-beat 1/8
               
               ])




)

