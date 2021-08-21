(ns musicality.compositions.2021-08-19
  (:require [clojure.spec.alpha :as s]
            [musicality.compose :refer :all]
            [musicality.schedule :refer :all])
)


(s/def :mu/note-num (s/and int?
                       #(>= % 0)
                       #(<= % 127)))

#_(s/valid? :mu/note 64)

(s/def :mu/dur (into #{} (keys dur->max-note-val)))

_#(s/valid? :mu/dur :1|2)

(s/def :mu/midi-note
  (s/cat :note int?
         :vel int?
         :dur :mu/dur))

#_(s/conform :mu/midi-note [127 0 :1|2])

(s/def :mu/cc (s/coll-of int?)) ;TODO
(s/def :mu/context (s/map-of [] []))
(s/def :mu/fn (s/coll-of keyword?))
(s/def :mu/note (s/and set?
                       (s/coll-of :mu/midi-note)))

#_(s/conform :mu/note #{[54 46 :1|2] [60 10 :1|1]})


(s/def :mu/beat 
  (s/keys :opt [:mu/cc
                :mu/context
                :mu/fn
                :mu/note]))

#_(s/conform :mu/beat {:mu/note #{[60 12 :1|1] [64 30 :1|2]}
                       :mu/fn #{:fn-name}})

(s/def :mu/sequence (s/map-of int? :mu/beat))

#_(s/conform :mu/sequence
           {1 {:mu/note #{[64 32 :1|1]}}
            69 {:mu/fn #{:do-it :go-go-go}}})


(s/valid? :mu/sequence {1 {:note 123}})

(defn get-bpm
  "returns the bpm to set in DAW to obtain a tatum-count that will equally map to a bpm-target and seq-beat-cnt target"
  [seq-beat-cnt bpm-target tatum-cnt]
  (let [mbpb 24
        mb-cnt (* seq-beat-cnt mbpb)]
    (float (* bpm-target (/ tatum-cnt mb-cnt)))))

#_(get-bpm 48 120 (* 12 420)) 12-bar blues as 12 bars of 420 tatums
#_(get-bpm 4 120 420)

(comment "TODO
## pmc/live-compose

* new musicality.compositions.YYYY-MM-DD
 - insert (:requre)
* cider jack-in
 - don't open new window"

         "
## clojure

* literate clojure? markdown interspersed? good for presenting and explaining pieces for public consumption
* customize cider-format-buffer, learn/set shortcut
* music programming lexicon. Is there a working group maintaining one? What's out there?

")
