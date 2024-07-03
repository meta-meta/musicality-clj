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



;see: Musicality.NoteType
(def note-types #{:EDO
                  :Irrational
                  :JI
                  :RawFreq
                  :UnpitchedMidi})

(defn tonnegg+ [id note-type val]
  (osc/send "/react/tonnegg" (int id)
            (json/write-str {:Note (merge {
                                           :NoteType (name note-type)
                                           }

                                          (cond
                                            (= note-type :JI)
                                            {:Val {
                                                   :Numerator   (numerator val)
                                                   :Denominator (denominator val)
                                                   }
                                             }

                                            (= note-type :EDO)
                                            {:Val             val
                                             :OctaveDivisions 12
                                             }

                                            )
                                          )})))

(tonnegg+ 0 :JI 3/2)
(tonnegg+ 0 :EDO 64)