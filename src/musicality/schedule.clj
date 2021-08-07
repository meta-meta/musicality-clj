(ns musicality.schedule
  (:use [musicality.osc :as o])
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


(defn send-beat
  "Sends a 'beat' of data to the sequencer. 
  beat is 1-based.  
  type can be :note :cc :fn.

  type -> data
  :note -> [note vel dur]
  :cc -> [cc target-val dur]
  :fn -> fn-name"
  [beat type data]
  (let [addr (str "/midiSeq/" (name type) "/" beat)
        msg (if (= type :fn)
              [(name data)]
              (map
               #(cond
                  (number? %) (int %)
                  (keyword? %) (dur->max-note-val %)
                  :else %)
               data))]
    (apply o/send addr msg)))


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
  "Sends a fn to executed at beat/sub-beat"
  ([beat sub-beat fn-keyword fn]
   (swap! fns assoc fn-keyword fn)
   (send-beat beat sub-beat :fn fn-keyword))
  ([beat fn-keyword fn] (send-fn beat 1 fn-keyword fn)))

(def ^:private seqs (atom {}))

;; TODO should beats have types merged or as separate sequences?
;; TODO send sub-beats in send-beat
(defn send-seq "Sends a sequence of beats. if s is a keyword, lookup in sequences"
  [type s]
  (doseq [[beat data] (map-indexed
                       (fn [idx data] [(+ 1 idx) data])
                       (if (keyword? s)
                         (@seqs s)
                         s))]
    (when-not (empty? data)
      (if (every? #(or (number? %) (keyword? %)) data)
        (send-beat beat 1 type data)
        (doseq [[sub-beat sub-data] (map-indexed
                                     (fn [idx sub-data] [(+ 1 idx) sub-data])
                                     data)]
          (when-not (empty? sub-data)
            (send-beat beat sub-beat type sub-data)))))))

(defn clear "clears all data in all beats. passes through a single argument for convenience"
  ([] (o/send "/midiSeq/clear"))
  ([anything] (clear) anything))

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

         (clear)

         ; fns are stored in an atom.
         @fns

         ; register a fn then schedule it
         (swap! fns assoc :fn1 #(println "fn1 executed"))
         (send-beat 1 :fn :fn1)
         (send-beat 3 :fn :fn1)

         ; or register and schedule in one shot.
         (send-fn 4 :fn2 #(println "four"))

         (send-beat 1 4 :note [70 60])
         (send-beat 1 :note [60 23 64 24 67 23 71 51])
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
           (send-beat 1 n :note [(+ 60 n) 60])))

