;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname queens-project-solution) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
(require racket/list)

;; ==========
;; Constants:
(define SIZE 4) ; the size of the board / problem

;; =================
;; Data definitions:

;; Row is Natural[0 SIZE)
;; Col is Natural[0 SIZE)

;; Board is (listof Col) that is =< SIZE elements long
;; interp. each value represents the column of a queen. 
;; Assume: queens are placed from the top row, advancing one row/queen
(define EBD empty)
(define BD0 (list 0))
(define BD1 (list 1))
(define BD2 (list 2))
(define BD3 (list 0 1)) ; invalid
(define BD4 (list 3 0 2))
(define BD5 (list 1 3 0 2))
(define BD6 (list 1 1)) ; invalid


(define-struct queen (row col))
;; Queen is (make-queen Row Col)
;; interp. a queen at position row, col
(define Q1 (make-queen 1 1))

;; Board -> (listof Queen) 
;; Takes in a linearly represented board and returns a list of queens
;; (by augumenting the Board data structure with the row values)
(check-expect (board->queens empty) empty)
(check-expect (board->queens (list 1 2 3)) (list (make-queen 2 1)
                                                 (make-queen 1 2)
                                                 (make-queen 0 3)))
(check-expect (board->queens (list 1 3 0 2)) (list (make-queen 3 1)
                                                   (make-queen 2 3)
                                                   (make-queen 1 0)
                                                   (make-queen 0 2)))

; (define (board->queens bd) empty) ;stub

(define (board->queens bd)
  (cond [(empty? bd) empty]
        [else (cons 
               (make-queen (sub1 (length bd)) (first bd)) 
               (board->queens (rest bd)))]))


;; =================
;; Functions:

;; Board -> (listof Board) or empty
;; produce a list of solutions for bd; or empty if bd is unsolvable
;; Assume: bd is valid
(check-expect (solve (list 1)) (list (list 2 0 3 1))) ; a pre-placed queen on the 2nd column
(check-expect (solve (list 2)) (list (list 1 3 0 2))) ; a pre-placed queen on the 3rd column
(check-expect (solve empty) (list (list 2 0 3 1)
                                  (list 1 3 0 2))) ; empty board => 2 solutions

;(define (solve bd) empty) ;stub

(define (solve bd)
  (local [(define (solve--bd bd)
            (local [(define solve--next
                      (solve--lobd (next-boards bd)))
                    (define (solved? bd)
                      (eq? (length bd) SIZE))]
              (cond [(solved? bd) (cons bd solve--next)]
                    [else solve--next])))
          
          (define (solve--lobd lobd)
            (cond [(empty? lobd) empty]
                  [else (append (solve--bd (first lobd)) (solve--lobd (rest lobd)))]))]
    
    (solve--bd bd)))

;; Board -> (listof Board)
;; produce list of valid next boards from a valid board
(check-expect (next-boards BD0) (list (cons 2 BD0)
                                      (cons 3 BD0)))

(check-expect (next-boards BD4) (list (cons 1 BD4)))
(check-expect (next-boards EBD) (list (list 0)
                                      (list 1)
                                      (list 2)
                                      (list 3)))

;(define (next-boards bd) empty) ;stub

(define (next-boards bd)
  (local [(define (place-queen p)
            (cons p bd))]
    (filter is-valid-bd? (map place-queen (range 0 SIZE)))))

;; Board -> Boolean
;; produces true if the last placed queen doesn't trheaten any
;; of the other queens
(check-expect (is-valid-bd? EBD) true) ; empty boards are valid
(check-expect (is-valid-bd? BD3) false)
(check-expect (is-valid-bd? BD6) false)

;(define (is-valid-bd? bd) false) ;stub

(define (is-valid-bd? bd)
  (if (empty? bd) 
      true
      (local [(define queens (board->queens bd))
              (define q1 (first queens))
              (define (doesnt-threaten q2)
                (local [(define cdiff (- (queen-col q1) (queen-col q2)))]
                  (cond [(eq? 0 cdiff) false]
                        [(eq? 1 (abs (/ cdiff (- (queen-row q1) (queen-row q2))))) false]
                        [else true])))]
        (andmap doesnt-threaten (rest queens)))))

;; =================
;; Display Functions

;; Board -> String
;; produces a string representation of a board (Q = queen; . = empty square)
(check-expect (board->string EBD) "")
(check-expect (board->string BD3) 
"
Q . . . 
. Q . . 
")

(check-expect (board->string BD5)
"
. Q . . 
. . . Q 
Q . . . 
. . Q . 
")

;(define (board->string bd) "") ;stub

(define (board->string bd)
  (cond [(empty? bd) ""]
        [else 
         (local [(define (col->string c)
                   (string-append 
                    (replicate c ". ")
                    "Q "
                    (replicate (- SIZE (add1 c)) ". ")
                    (string #\newline)
                    ))]
           (apply string-append (cons (string #\newline) (map col->string bd))))]))


(apply error 
       (map board->string 
            (reverse (solve empty))))