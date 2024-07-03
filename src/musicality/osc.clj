(ns musicality.osc
  (:use [overtone.osc :only (osc-client osc-close osc-handle osc-rm-handler osc-send osc-server)])
  (:gen-class))

;; TODO: musicality.live-compose has dep injection to send/receive OSC
;; TODO: could midi sync directly via https://github.com/overtone/midi-clj instead of using Max

(defn connect [ip]
  (def client (osc-client ip 8020))
  (def server (osc-server 9004)))

(defn connect-local [] (connect "localhost"))

(defn disconnect []
  (osc-close server)
  )

(defn handle [addr handler]
  (osc-handle server addr handler))

(defn send [addr & args]
  (apply osc-send client addr args))


#_(disconnect)
#_(connect-local)
