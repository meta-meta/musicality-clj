(ns musicality.compositions.2021-08-28
  (:require [musicality.schedule :as s]
            [musicality.compose :as c]))

#_(s/init)

#_(s/deinit)

(defn send [])


(defn bpm->tatum-millis [bpm]
  (* 1000
     (/ 60 bpm 24)))

(def tatum-millis (bpm->tatum-millis 960))

(comment

  (s/clear "bird")


  (s/send-beat "bird" 100 :cc [0 1 0 (* 400 tatum-millis)])
  (s/send-beat "bird" 0 :cc [0 1 1.0 (* 10 tatum-millis)])
  (s/send-beat "bird" 300 :cc [0 3 3 0.5 (* 200 tatum-millis)])


  (s/send-beat "bird" 0 :cc [1 1 1.0 (* 400 tatum-millis)])
  (s/send-beat "bird" 400 :cc [1 1 0.0 (* 10 tatum-millis)])


  (s/send-beat "bird" 1 :cc [2 3 0.0 0.4 (* 420 tatum-millis)])
  (s/send-beat "bird" 1 :cc [3 3 0.0 0.5 (* 420 tatum-millis)])
  (s/send-beat "bird" 1 :cc [4 3 1.0 0.7 (* 420 tatum-millis)])
  (s/send-beat "bird" 1 :cc [5 3 0.0 0.9 (* 420 tatum-millis)])

  (s/send-beat "bird" 1 :cc [6 1 0.0])
  (s/send-beat "bird" 10 :cc [6 2 0.3 (* 50 tatum-millis)])
  (s/send-beat "bird" 80 :cc [6 2 0.1 (* 20 tatum-millis)])
  (s/send-beat "bird" 100 :cc [6 2 0.8 (* 150 tatum-millis)])

  (s/send-beat "bird" 1 :cc [7 3 0.0 2.0 (* 420 tatum-millis)])

  (s/send-beat "bird" 1 :cc [8 2 0.5 (* 40 tatum-millis)])
  (s/send-beat "bird" 50 :cc [8 2 0.3 (* 70 tatum-millis)])
  (s/send-beat "bird" 200 :cc [8 2 0.8 (* 200 tatum-millis)])

  (s/send-beat "bird" 1 :cc [9 2 0.4 (* 30 tatum-millis)])
  (s/send-beat "bird" 50 :cc [9 2 0.7 (* 70 tatum-millis)])
  (s/send-beat "bird" 200 :cc [9 2 1.0 (* 200 tatum-millis)])

  (s/send-beat "bird" 1 :cc [10 2 1.0 (* 105 tatum-millis)])
  (s/send-beat "bird" 105 :cc [10 3 0.0 3.0 (* 210 tatum-millis)])
  (s/send-beat "bird" 315 :cc [10 2 0.0 (* 100 tatum-millis)]))

