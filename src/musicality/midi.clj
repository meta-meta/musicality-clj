(ns musicality.midi
  (:require [overtone.midi :as m]))

(def x-touch-mini-in (m/midi-in "X-TOUCH MINI"))
(def x-touch-mini-out (m/midi-out "X-TOUCH MINI"))


#_(m/midi-in)

#_(:receiver x-touch-mini-in)
#_(.getReceiver (:transmitter x-touch-mini-in))

#_(.open (:device x-touch-mini-in))

; TODO: figure out how to dynamically intercept/passthrought MIDI to LM
; https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Transmitter.html

(def lm-x-touch-mini-in (m/midi-in "LM-X-Touch Mini"))
(def lm-x-touch-mini-out (m/midi-out "LM-X-Touch Mini"))


(def fcb1010 (m/midi-in "USB2.0-MIDI"))


(defn print-msg [msg]
  (->> [:command :channel :note :velocity]
     (map #(% msg))
     (interpose " ")
     (apply str (:name (:device msg)) " ")
     (prn)))

(defn handle [msg] (print-msg msg))
(m/midi-handle-events x-touch-mini-in #'handle)

(m/midi-handle-events fcb1010 #'handle)

(defn handle-lm [msg] (print-msg msg))
(m/midi-handle-events lm-x-touch-mini-in #'handle-lm)

(defn list-midi-ins []
  (->> (m/midi-sources)
       (map #(:name %))))

(defn list-midi-outs []
  (->> (m/midi-sinks)
       (map #(:name %))))

#_(clojure.repl/dir overtone.midi)
#_(clojure.repl/doc m/midi-handle-events)

(comment ""
         (m/midi-note-on x-touch-mini-out 23 127 10)
         (m/midi-note-off x-touch-mini-out 23 10)

         (m/midi-control x-touch-mini-out 1 64 10)

         (list-midi-outs)
         (list-midi-ins)


         (m/midi-route x-touch-mini-in lm-x-touch-mini-out))
