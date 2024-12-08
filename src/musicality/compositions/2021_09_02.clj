(ns musicality.compositions.2021-09-02
  (:require [musicality.compose :as c]
            [musicality.osc :as o]
            [musicality.schedule :as s]))

#_(s/init)
#_(s/deinit)
#_(o/send-osc "/pianoteq/oscMsg/host" "192.168.1.33")
#_(o/send-osc "/pianoteq/oscMsg/port" "9000")


#_(s/send-beat "pianoteq" 1 :oscMsg ["/oled/print" "help" 0 0 35])
#_(s/send-beat "pianoteq" 100 :oscMsg ["/oled/clear" 1])


#_(->> [0]
     ;(map #(* 4 %))
     
     (map-indexed (fn [idx beat] 
                   
                    ;; TODO: too slow. these block everything while updated the oled
                    ;; (s/send-beat "pianoteq" (+ beat 1) :oscMsg ["/oled/clear"])
                    ;; (s/send-beat "pianoteq" (+ beat 2) :oscMsg ["/oled/print" "poo" 0 0 35])

                    (s/send-beat "pianoteq" beat :oscMsg 
                                 (concat ["/led/set"]
                                         (->> (range 12)
                                              (map (fn [i] [i (/ (mod (+ idx i) 12) 12) 1 0.005]))
                                              (flatten)))))))


(defn send-beat [beat leds]

 ;; TODO need multiple oscMsgs per beat
 #_(s/send-beat "pianoteq" (+ 1 beat) :oscMsg
               (concat ["/oled/print"]
                       ()))

  (s/send-beat "pianoteq" beat :oscMsg 
               (concat ["/led/set"]
                       (->> leds
                            (map-indexed (fn [i v] [i (/ i 12) 1 (* v 0.005)]))
                            (flatten)))))

#_(send-beat 0 [1 0 0 0 1 0 0 1 0 0 1 0])
#_(send-beat 0 [1 0 1 0 1 1 0 1 0 1 0 1])


#_(->> (range (/ 420 35))
     (map #(* 35 %))
     (map-indexed (fn [idx beat] 
                    (s/send-beat "pianoteq" beat :oscMsg 
                                 (concat ["/led/add"]
                                         (->> (range 12)
                                              (map (fn [i] [i 0 0 0.004]))
                                              (flatten))))

                    (s/send-beat "pianoteq" (+ 1 beat) :oscMsg 
                                 (concat ["/led/add"]
                                         (->> (range 12)
                                              (map (fn [i] [i 0 0 0]))
                                              (flatten))))
                    
                    )))



#_(s/send-beat "pianoteq" 0 :oscMsg ["/led/set" 0 0 0 1])
#_(s/send-beat "pianoteq" 30 :oscMsg ["/led/set" 6 0.3 1 1])

#_(s/clear "pianoteq")
