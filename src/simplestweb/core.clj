(ns simplestweb.core
 (:use [org.httpkit.server :only [run-server]])
 (:require [org.httpkit.client :as kithttp]
           [clj-jgit.porcelain :as p]
           [clj-jgit.querying :as q]
           [clj-jgit.internal :as i]
           [clojure.string :as string]))

(defn app [req]
  (let [my-wdir (-> (java.io.File. ".") .getAbsolutePath)
        my-repo (p/load-repo (str my-wdir "/.git"))
        my-logs (map #(q/commit-info my-repo %) (p/git-log my-repo))]
      {:status  200
       :headers {"Content-Type" "text/html"}
       :body    (str "The log of this app is..<br>"
                      my-wdir "<br/>"
                     (string/join "<br/>" (map #(str (:id %) " - " (:message %)) my-logs)))
       }))

(defn -main [& args]
  (run-server app {:port 8080})
  (println (str "Server started. listen at localhost@8080")))
