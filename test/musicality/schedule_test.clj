(ns musicality.schedule-test
  (:require [clojure.spec.alpha :as s]
            [expectations.clojure.test
             :refer [defexpect expect expecting
                     approximately between between' functionally
                     side-effects]]
            [musicality.osc :as o]
            [musicality.schedule :refer :all]))

(defexpect send-beat-:note
  (expect [["/midiSeq/note/3" 60 127 "2n"]]
          (side-effects [o/send] (send-beat 3 :note [60 127 :1|2]))))

(defexpect send-beat-:cc
  (expect [["/midiSeq/cc/13" 10 64]]
          (side-effects [o/send] (send-beat 13 :cc [10 64]))))

(defexpect send-beat-:fn
  (expect [["/midiSeq/fn/20" "my-func"]]
          (side-effects [o/send] (send-beat 20 :fn "my-func"))))

