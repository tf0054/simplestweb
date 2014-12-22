(ns simplestweb.core
 (:use [org.httpkit.server :only [run-server]])
 (:require [org.httpkit.client :as kithttp]))
 
(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello HTTP kit!"})
 
(defn -main [& args]
  (run-server app {:port 8080})
  (println (str "Server started. listen at localhost@8080")))
