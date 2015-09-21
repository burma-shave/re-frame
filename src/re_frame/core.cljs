(ns re-frame.core
  (:require
    [re-frame.handlers :as handlers]
    [re-frame.subs :as subs]
    [re-frame.router :as router]
    [re-frame.utils :as utils]
    [re-frame.middleware :as middleware]
    [reagent.core :as reagent]))

(def app-db (reagent/atom {}))

(def event-chan (chan))

;; maps from handler-id to handler-fn
(def ^:private key->fn (atom {}))

;; -- the register of event handlers --------------------------------------------------------------
(def ^:private id->fn  (atom {}))

(router/router-loop id->fn app-db event-chan)

;; --  API  -------
(defn dispatch [event-v]
  (router/dispatch event-chan event-v))
(def dispatch-sync    router/dispatch-sync)

(def register-sub (partial subs/register key->fn))
(def clear-sub-handlers! (partial subs/clear-handlers! key-fn))
(def subscribe (partial subs/subscribe key->fn))


(def clear-event-handlers! (partial handlers/clear-handlers! id->fn))


(def pure        middleware/pure)
(def debug       middleware/debug)
;(def undoable    middleware/undoable)
(def path        middleware/path)
(def enrich      middleware/enrich)
(def trim-v      middleware/trim-v)
(def after       middleware/after)
(def log-ex      middleware/log-ex)


;; ALPHA - EXPERIMENTAL MIDDLEWARE
(def on-changes  middleware/on-changes)


;; --  Logging -----
;; re-frame uses the logging functions: warn, log, error, group and groupEnd
;; By default, these functions map directly to the js/console implementations
;; But you can override with your own (set or subset):
;;   (set-loggers!  {:warn  my-warn   :log  my-looger ...})
(def set-loggers! utils/set-loggers!)


;; --  Convenience API -------

;; Almost 100% of handlers will be pure, so make it easy to
;; register with "pure" middleware in the correct (left-hand-side) position.
(defn register-handler
  ([id handler]
    (handlers/register-base id->fn id pure handler))
  ([id middleware handler]
    (handlers/register-base id->fn id [pure middleware] handler)))



