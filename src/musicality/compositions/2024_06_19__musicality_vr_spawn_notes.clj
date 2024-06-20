(ns musicality.compositions.2024-06-19--musicality-vr-spawn-notes
  (:require [musicality.osc :as osc]
            [clojure.data.json :as json]))

(osc/connect-local)


(defn midi-beat-clock-wheel+ [id ratio beats pulses]
  (osc/send "/react/midiBeatClockWheel" (int id)
            (json/write-str {
                             :Beats      beats
                             :Pulses     pulses
                             :ClockRatio {
                                          :Val {
                                                :Numerator   (numerator ratio)
                                                :Denominator (denominator ratio)
                                                }
                                          }
                             })))

(defn midi-beat-clock-wheel- [id] (osc/send "/react/midiBeatClockWheel" (int id)))

(midi-beat-clock-wheel+ 1 3/7 1 12)
(midi-beat-clock-wheel- 1)
