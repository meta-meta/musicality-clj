(defproject musicality "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[com.github.seancorfield/expectations "2.0.0-alpha2"]
                 [org.clojure/clojure "1.10.0"]
                 [overtone/osc-clj "0.8.1"]
                 [scad-clj "0.5.3"]]
  :main ^:skip-aot musicality.core
  :target-path "target/%s"
  :plugins [[lein-codox "0.10.7"]]
  :codox {:namespaces :all}
  :profiles {:uberjar {:aot :all}})
