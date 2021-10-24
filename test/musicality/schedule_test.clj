(ns musicality.schedule-test
  (:require [clojure.spec.alpha :as s]
            [expectations.clojure.test
             :refer [defexpect expect expecting
                     approximately between between' functionally
                     side-effects]]
            [musicality.osc :refer [send]]
            [musicality.schedule :refer :all]))

(defexpect send-beat-test
  (defexpect note
    (expect [["/drums/note/3" 60 127 "2n" 62 64 "1n" ]]
            (side-effects [send] (send-beat "drums" 3 :note #{[60 127 :1|2] [62 64 :1|1] }))))

  (defexpect cc
    (expect [["/pianoteq/cc/13" 10 64]]
            (side-effects [send] (send-beat "pianoteq" 13 :cc [10 64]))))

  (defexpect fn
    (expect [["/pianoteq/fn/20" "my-func"]]
            (side-effects [send] (send-beat  "pianoteq" 20 :fn "my-func")))))

