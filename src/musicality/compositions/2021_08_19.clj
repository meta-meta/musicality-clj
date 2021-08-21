(ns musicality.compositions.2021-08-19
  (:require [clojure.spec.alpha :as s]
            [musicality.compose :refer :all]
            [musicality.schedule :refer :all])
)


(s/def ::note-num (s/and int?
                       #(>= % 0)
                       #(<= % 127)))

#_(s/valid? ::note 64)

(s/def ::dur (into #{} (keys dur->max-note-val)))

#_(s/valid? ::dur :1|2)

(s/def ::midi-note
  (s/cat :note int?
         :vel int?
         :dur :mu/dur))

#_(s/conform ::midi-note [127 0 :1|2])

(s/def ::cc (s/coll-of int?)) ;TODO
(s/def ::context (s/map-of [] []))
(s/def ::fn (s/coll-of keyword?))
(s/def ::note (s/and set?
                       (s/coll-of ::midi-note)))

#_(s/conform ::note #{[54 46 :1|2] [60 10 :1|1]})


(s/def ::beat 
  (s/keys :opt-un [::cc
                   ::context
                   ::fn
                   ::note]))

#_(s/conform ::beat {:note #{[60 12 :1|1] [64 30 :1|2]}
                       :fn #{:some-func :some-other-func}})

(s/def ::sequence (s/map-of int? ::beat))

#_(s/conform ::sequence
           {1 {:note #{[64 32 :1|1]}}
            420 {:fn #{:do-it :go-go-go}}})


#_(s/valid? ::sequence {1 {:note 123}})

(defn get-bpm
  "returns the bpm to set in DAW to obtain a tatum-count that will equally map to a bpm-target and seq-beat-cnt target"
  [seq-beat-cnt bpm-target tatum-cnt]
  (let [mbpb 24
        mb-cnt (* seq-beat-cnt mbpb)]
    (float (* bpm-target (/ tatum-cnt mb-cnt)))))

#_(get-bpm 48 120 (* 12 420)) ; 12-bar blues as 12 bars of 420 tatums
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
