(ns musicality.osc-reaper
  (:use [overtone.osc :only (osc-client osc-close osc-handle osc-rm-handler osc-send osc-server in-osc-bundle)])
  (:gen-class))

;; TODO: musicality.live-compose has dep injection to send/receive OSC
;; TODO: could midi sync directly via https://github.com/overtone/midi-clj instead of using Max

(def state (atom {
                  :client nil
                  :server nil
                  }))

(defn connect [ip]
  (swap! state assoc :client (osc-client ip 9666))          ; REAPER
  (swap! state assoc :server (osc-server 9616))             ; this
  )

(defn client [] (:client @state))
(defn server [] (:server @state))

(defn connect-local [] (connect "localhost"))

(defn disconnect []
  (osc-close (server))
  )

(defn handle [addr handler]
  (osc-handle (server) addr handler))

(defn send-osc [addr & args]
  (apply osc-send (client) addr args))


(defn bpm-set [bpm] (send-osc "f/tempo/raw" (float bpm)))
(defn pause [] (send-osc "t/pause"))
(defn play [] (send-osc "t/play"))
(defn record [] (send-osc "t/record"))
(defn stop [] (send-osc "t/stop"))
(defn click [] (send-osc "t/click"))

#_(disconnect)
#_(connect-local)
