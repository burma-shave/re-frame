(ns re-frame.system)

;(defprotocol Dispatcher
;  (dispatch [event-v]))
;
;(defrecord Router [event-chan]
;  component/Lifecycle
;  (start [component]
;    ;; start event processing
;    (router-loop event-chan))
;  (stop [component]
;    (stop event-chan))                                  ; closes the channel
;  Dispatcher
;  (dispatch [event-v]
;    (dispatch event-chan event-v)))