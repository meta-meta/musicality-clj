(ns musicality.schedule
  (:use [musicality.osc :as o])
  (:gen-class))

(defn send-beat
  "Sends a 'beat' of data to the sequencer. 
  beat is 1-based; sub-beat is 1-based subdivision of beat.
  The number of sub-beats is determined by timesig:
  when beats are 8th notes, there are 12 sub-beats. quarter notes have 24 sub-beats.
  type can be :note :cc :fn.

  type -> data
  :note -> [note vel]
  :cc -> [cc val]
  :fn -> fn-name"
  ([beat sub-beat type data]
   (let [addr (str "/midiSeq/" (name type) "/" beat "/" sub-beat)
         msg (if (= type :fn)
               [(name data)]
               (map #(if (number? %) (int %) %) data))]
     (apply o/send addr msg)))
  ([beat type data]
   (send-beat beat 1 type data)))

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
      (if (every? #(number? %) data)
        (send-beat beat 1 type data)
        (doseq [[sub-beat sub-data] (map-indexed
                                     (fn [idx sub-data] [(+ 1 idx) sub-data])
                                     data)]
          (when-not (empty? sub-data)
            (send-beat beat sub-beat type sub-data)))))))

(defn clear "clears all data in all beats"
  [] (o/send "/midiSeq/clear"))

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

         ; register a fn then schedule it
         (swap! fns assoc :fn1 #(println "fn1 executed"))
         (send-beat 1 :fn :fn1)
         (send-beat 3 :fn :fn1)

         ; or register and schedule in one shot.
         (send-fn 4 :fn2 #(println "four"))

         (send-beat 1 4 :note [70 60])
         (send-beat 1 :note [60 23 64 24 67 23 71 51])
         (send-beat 1 :note [60 64 66 60])

         ; a 4 chord waltz
         (send-seq :note
                   [[62 64 65 64 69 64] [50 64] [50 64]
                    [67 64 71 64 74 64] [55 64] [55 64]
                    [60 64 64 64 67 64] [48 64] [48 64]
                    [64 64 67 64 71 64] [52 64] [52 64]])

         ; same thing but with flourish in sub-beats
         (send-seq :note
                   [[62 64 65 64 69 64] [50 64] [50 64]

                    [67 64 71 64 74 64]
                    [[55 64] [] [] [] [] []
                     [57 64] [] [] [] [] [] []]
                    [[55 64] [] [] [] [] []
                     [57 64] [] [] [] [] []]

                    [60 64 64 64 67 64] [48 64] [48 64]
                    [64 64 67 64 71 64] [52 64] [52 64]])

         ; or should sub-beats automatically spread evenly? ...probably out of scope. do this in compose
         (send-seq :note
                   [[62 64 65 64 69 64] [50 64] [50 64]
                    [67 64 71 64 74 64] [[55 64] [57 64]] [[55 64] [57 64]]
                    [60 64 64 64 67 64] [48 64] [48 64]
                    [64 64 67 64 71 64] [[52 64] [54 64] [56 64]] [52 64]])


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

