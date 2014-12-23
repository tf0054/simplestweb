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
     :body    (str "<DIV STYLE=\"font-family: Consolas, Menlo, 'Liberation Mono', Courier, monospace;\">"
                   "The log of the app stored at "
                   (subs my-wdir 0 (- (.length my-wdir) 2))
                   " is ...<br/><br/>"
                   (string/join "<br/>" (map #(str (:id %) " - "
                                                   (:time %) " - "
                                                   (:message %)) my-logs))
                   "</DIV>")
     }))

(defn -main [& args]
  (let [port 80]
    (run-server app {:port port})
    (println (str "Server started. listen at localhost@" port))))
