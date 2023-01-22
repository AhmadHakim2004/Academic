/*
 *  CSC A48 - Winter 2022 - Assignment 1 starter
 * 
 *  (c) 2020-22 Francisco Estrada
 *  - No part of this code may be reproduced without written authorization
 * 
 * This is the file where you will be doing most of your work. The
 * functionality you must provide for part 1 of the assignment is described
 * in the handout. Given in detail in the comments at the head of each
 * function below. 
 * 
 * Plan your work carefully, review the notes for Unit 3, and work carefully
 * to complete the functions in this file. You can bring 
 * questions to your TAs or instructors during office hours. Please
 * remember:
 * 
 * - You should not share any part of your solution in any form
 * - You should not post any assignment code on Piazza
 * - You should definitely *help* other understand any ideas and
 *   concepts regarding linked lists that you have already mastered,
 *   but being careful not to give away parts of the solution, or
 *   descriptions of how to implement functions below.
 * - If you are not sure whether you can or can not discuss some
 *   particular aspect of the work to be done, remember it's always 
 *   safe to talk with your TAs.
 * - Obtaining external 'help' including being given code by an
 *   external party, or being tutored on how to solve
 *   the assignment constitutes an academic offense.
 * 
 * All tasks to be completed by you are clearly labeled with a
 * ***** TO DO ****** comment block, which also gives you details
 * about what you have to implement. Look carefully and make sure
 * you don't miss a thing!
 * 
 * NOTE: This file contains no main() function! you can not compile
 * it on its own to create an executable. It's meant to be used
 * together with Part1_driver.c - be sure to read that file carefully
 * to understand how to use the tests there - Any additional tests
 * you want to run on the code below should be added to Part1_driver.c
 * 
 * Before you even get starter implementing, please complete the
 * student identification section below, and check that you are aware
 * of the policy on academic honesty and plagiarism.
 */

/* Student identification:
 * 
 * Student Name (Last name, First name): Hakim, Ahmad
 * Student Number: 1007904207
 * UTORid: hakimahm
 * Your instructor's name is: Marcelo
 */

/* Academic Integrity Statement:
 * 
 * I hereby certify that the work contained in this file is my own, and
 * that I have not received any parts of my solution from other sources
 * including my fellow students, external tutoring services, the internet,
 * or algorithm implementations found online.
 * 
 * Sign here with your name: Ahmad Hakim
 *
 *  
 */

#include<stdbool.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define MAX_STR_LEN 1024

/* Compound data type declarations */
/***************************************************************************/
/******                         TO DO                               ********/
/****** In the space below, complete the definitions of the compound *******/
/****** data types that will be needed to implement the movie review *******/
/****** linked list. This includes the MovieReview type, and the     *******/
/****** ReviewNode. Details about the contents of these can be       *******/
/****** found in the assignment handout. Read them carefully!        *******/
/******                                                              *******/
/****** AFTER completing the basic linked list, complete the CDT     *******/
/****** required to implement a list for the movie's cast.           *******/
/***************************************************************************/

typedef struct castList_struct
{    
	char name[MAX_STR_LEN];
	float salary;
	struct castList_struct *next;
    
} CastList;

typedef struct movieReview_struct
{
	char movie_title[MAX_STR_LEN];
	char movie_studio[MAX_STR_LEN];
	int year;
	float BO_total;
	int score;
	CastList *cast;
	
    
} MovieReview;

typedef struct reviewNode_struct
{
	MovieReview review;
	 struct reviewNode_struct *next;
    
} ReviewNode;


	

ReviewNode *newMovieReviewNode()
{
    /*
     * This function allocates an empty ReviewNode, and initializes the
     * contents of the MovieReview for this node to reasonable (uninitialized) values.
     * The fields in the MovieReview should be set to:
     *  movie_title=""
     *  movie_studio=""
     *  year = -1
     *  BO_total = -1
     *  score = -1
     *  scoreList = NULL;
     * 
     * The *next pointer for the new node MUST be set to NULL
     * 
     * The function must return a pointer to the newly allocated and initialized
     * node. If something goes wrong, the function returns NULL
     */
 
    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/

	ReviewNode *new_node=NULL;

	new_node=(ReviewNode *)calloc(1, sizeof(ReviewNode));


	strcpy(new_node->review.movie_title,"");
	strcpy(new_node->review.movie_studio,"");
	new_node->review.year=-1;
	new_node->review.BO_total=-1;
	new_node->review.score=-1;
	new_node->review.cast=NULL;
	new_node->next=NULL;

    
    return(new_node);    
}

ReviewNode *findMovieReview(char title[MAX_STR_LEN], char studio[MAX_STR_LEN], int year, ReviewNode *head)
{
    /*
     * This function searches through the linked list for a review that matches the input query. 
     * The movie review must match the title, studio, and year provided in the 
     * parameters for this function.
     * 
     * If a review matching the query is found, this function returns the address of the node that
     * contains that review. 
     * 
     * If no such review is found, this function returns NULL
     */
    
    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/
	ReviewNode *p=NULL;
	p=head;
	while (p!=NULL) {
		if (  ( strcmp( p -> review.movie_title, title ) ) == 0 && ( strcmp( p -> review.movie_studio, studio) ) == 0 && ( p -> review.year == year ) ) {
			return p;
		}
		p = p -> next;
	}
	return NULL;  
}

ReviewNode *insertMovieReview(char title[MAX_STR_LEN], char studio[MAX_STR_LEN], int year, float BO_total, int score, ReviewNode *head)
{
    /*
     * This function inserts a new movie review into the linked list.
     * 
     * The function takes as input parameters the data neede to fill-in the review,
     * as well as apointer to the current head of the linked list.
     * 
     * If head==NULL, then the list is still empty.
     * 
     * The function inserts the new movie review *AT THE HEAD* of the linked list,
     * and returns the pointer to the new head node.
     * 
     * The function MUST check that the movie is not already in the list before
     * inserting (there should be no duplicate entries). If a movie with matching
     * title, studio, and year is already in the list, nothing is inserted and the
     * function returns the current list head.
     */
    
    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/
	

		if ( findMovieReview(title, studio, year, head) == NULL ) {
 			ReviewNode *movie_to_add = newMovieReviewNode();
	
			strcpy(movie_to_add->review.movie_title, title);
			strcpy(movie_to_add->review.movie_studio, studio);
			movie_to_add->review.year = year;
			movie_to_add->review.BO_total = BO_total;
			movie_to_add->review.score = score;
			movie_to_add->next=head;
			
			head = movie_to_add;
		}
		
		else {
			printf("The movie is already in the Database\n");
		}

	return head; 
}

int countReviews(ReviewNode *head)
{
  /*
   * This function returns the number of reviews. 
   */

    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/  
	int count = 0;
	ReviewNode *p=NULL;
	p=head;
	
	while (p != NULL) {
		count++;
		p = p-> next;
	}
	
    	return count; 
}

void updateMovieReview(char title[MAX_STR_LEN], char studio[MAX_STR_LEN], int year, float BO_total, int score, ReviewNode *head)
{
    /*
     * This function looks for a review matching the input query [title, studio, year].
     * If such a review is found, then the function updates the Box-office total, and the score.
     * If no such review is found, the function prints out 
     * "Sorry, no such movie exists in the database"
     */

    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/

		ReviewNode *movie_to_update = findMovieReview(title, studio, year, head);

		if ( movie_to_update == NULL ) {
			printf("Sorry, no such movie exists in the database\n");
		}
		
		else {
			movie_to_update->review.score = score;
			movie_to_update->review.BO_total = BO_total;
		}

}

ReviewNode *deleteMovieReview(char title[MAX_STR_LEN], char studio[MAX_STR_LEN],int year, ReviewNode *head)
{
    /*
     * This function removes a review matching the input query from the database. If no such review can
     * be found, it does nothing.
     * 
     * The function returns a pointer to the head of the linked list (which may have changed as a result
     * of the deletion process)
     */

    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/

	ReviewNode *movie_to_delete =  findMovieReview(title, studio, year, head);



	if (movie_to_delete != NULL) {
		ReviewNode *p=NULL;
		p=head;
		if ( p == movie_to_delete) {
			head = p -> next;
			free(p);
		}
		
		else {

			ReviewNode *q=NULL;
			while (p != movie_to_delete) {
				q=p;
				p = p -> next;
			}
			q -> next = p -> next;
			free(p);
		}
	}
	return head;
        
}

float printMovieReviews(ReviewNode *head)
{
    /*
     * This function prints out all the reviews in the database, one after another.
     * Each field in the review is printed in a separate line, with *no additional text*
     * (that means, the only thing printed is the value of the corresponding field).
     * 
     * Reviews are separated from each other by a line of
     * "*******************"

     * The function also computes and returns the Box-office total, for all the
     * movies that match the query.
     * 
     * See the A1 handout for a sample of the output that should be produced
     * by this function
     */
    
    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/


	float count = 0;
	ReviewNode *p=NULL;
	p=head;

	while (p != NULL) {

		printf("%s\n",p->review.movie_title);
		printf("%s\n",p->review.movie_studio);
		printf("%d\n",p->review.year);
		printf("%f\n",p->review.BO_total);
		printf("%d\n",p->review.score);
		CastList *ourCast = p->review.cast;
		while (ourCast != NULL) {
			printf("%s\n", ourCast -> name);
			printf("%f\n", ourCast -> salary);
			ourCast = ourCast -> next;
		}
		printf("**********************************************\n");	
		count += p->review.BO_total;
		p = p -> next;
	}

	return count;

}

float queryReviewsByStudio(char studio[MAX_STR_LEN], ReviewNode *head)
{
    /*
     * This function looks for reviews whose studio matches the input query.
     * It prints out the contents of all reviews matching the query in exactly
     * the same format used by the printMovieReviews() function above.
     * 
     * Additionally, it computes and returns the Box-office total, for all the
     * movies that match the query.
     */
    
    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/  
    

	float count = 0;
	ReviewNode *p=NULL;
	p=head;

	while (p != NULL) {
		if (strcmp(p->review.movie_studio, studio) == 0) {
			printf("%s\n",p->review.movie_title);
			printf("%s\n",p->review.movie_studio);
			printf("%d\n",p->review.year);
			printf("%f\n",p->review.BO_total);
			printf("%d\n",p->review.score);
			printf("**********************************************\n");	
			count += p->review.BO_total;
		}
		p = p -> next;
	}

	return count;


}

float queryReviewsByScore(int min_score, ReviewNode *head)
{
    /*
     * This function looks for reviews whose score is greater than, or equal to
     * the input 'min_score'.
     * It prints out the contents of all reviews matching the query in exactly
     * the same format used by the printMovieReviews() function above.
     * 
     * Additionally, it computes and returns the Box-office total, for all the
     * movies that match the query.
     */
    
    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/  
    
	float count = 0;
	ReviewNode *p=NULL;
	p=head;

	while (p != NULL) {
		if (p->review.score >= min_score) {
			printf("%s\n",p->review.movie_title);
			printf("%s\n",p->review.movie_studio);
			printf("%d\n",p->review.year);
			printf("%f\n",p->review.BO_total);
			printf("%d\n",p->review.score);
			printf("**********************************************\n");	
			count += p->review.BO_total;
		}
		p = p -> next;
	}

	return count;

}

ReviewNode *deleteReviewList(ReviewNode *head)
{
  /*
   * This function deletes the movie review database, releasing all the
   * memory allocated to the nodes in the linked list.
   * 
   * Returns a NULL pointer so that the head of the list can be set to NULL
   * after deletion.
   */
      
    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/    

	while (head != NULL) {
		ReviewNode *p=NULL;
		p = head;
		head = head -> next;
		free(p);
	}
		
    
	return head;
}



/* CRUNCHY SECTION! Do not work on the functions below until
 * your basic linked list is working properly and is fully tested!
 */ 


void changeMovie(ReviewNode *empty, ReviewNode *original) {

	strcpy(empty->review.movie_title, original->review.movie_title);
	strcpy(empty->review.movie_studio, original->review.movie_studio);
	empty->review.year = original->review.year;
	empty->review.BO_total = original->review.BO_total ;
	empty->review.score = original->review.score ;
	empty->review.cast = original->review.cast;

}


ReviewNode *sortReviewsByTitle(ReviewNode *head)
{
  /*
   * This function sorts the list of movie reviews in ascending order of movie
   * title. If duplicate movie titles exist, the order is arbitrary (i.e. you
   * can choose which one goes first).
   * 
   * However you implement this function, it must return a pointer to the head
   * node of the sorted list.
   */

    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/      


	if ( (head == NULL) ||  (head -> next == NULL) ) {
		return head;
	}
	else {
		ReviewNode *new_head = newMovieReviewNode();
		ReviewNode *last_movie = newMovieReviewNode();
		ReviewNode *x = head -> next;
		ReviewNode *movie_to_add = x -> next;
		if (  strcmp(head -> review.movie_title , x -> review.movie_title) <= 0) {
			changeMovie(new_head, head);
			changeMovie(last_movie, x);
		}

		else {
			changeMovie(new_head, x);
			changeMovie(last_movie, head);
		}

		new_head -> next = last_movie;
			

	
		while (movie_to_add != NULL) {
			ReviewNode *now_add = newMovieReviewNode();
			changeMovie(now_add, movie_to_add);
			if ( strcmp(movie_to_add  -> review.movie_title, new_head -> review.movie_title) <= 0 ) {

				now_add -> next = new_head;
				new_head = now_add;
			}
			
			else if ( ( strcmp(movie_to_add  -> review.movie_title, last_movie  -> review.movie_title) ) >= 0 ) {
				last_movie -> next = now_add;
				last_movie = now_add;
			
			}
			
			else {
				ReviewNode *movie_to_compare = new_head -> next;
				ReviewNode *movie_to_save = new_head;
				while( strcmp(movie_to_add  -> review.movie_title,  movie_to_compare  -> review.movie_title) > 0) {
					movie_to_save = movie_to_compare;
					movie_to_compare = movie_to_compare -> next;
				}
				
				movie_to_save -> next = now_add;
				now_add -> next = movie_to_compare;		
			}
			movie_to_add = movie_to_add -> next;

		}
		deleteReviewList(head);

		return new_head;

	}

}


CastList *newCast()
{
	CastList *new_node=NULL;

	new_node=(CastList *)calloc(1, sizeof(CastList));


	strcpy(new_node->name,"");
	new_node->salary=-1;
	new_node->next=NULL;

    
    return(new_node);    

}

void insertCastMember(char title[MAX_STR_LEN], char studio[MAX_STR_LEN], int year, ReviewNode *head, char name[MAX_STR_LEN], float salary)
{
  /*
   * This function inserts the name of a cast member for the given movie into the
   * linked list of cast members. The new cast member must go to the end of the list.
   * 
   * Duplicate names are allowed - this time! 
   * 
   * Notice the function receives the title, studio, and year for the movie, as
   * well as a pointer to the movie DB linked list. The function must find the 
   * correct movie and if such a movie exists, add the cast member's name to its
   * cast list.
   * 
   * If no such movie is found, this function does nothing.
   * 
   * You're free to add helper functions to insert the cast member's name
   * into the cast list.
   */   

    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/      
  	
	ReviewNode *movieWithCast = findMovieReview(title, studio, year, head);
	if (movieWithCast != NULL) {
		CastList *newMember = newCast();
		strcpy(newMember -> name,name);
		newMember -> salary = salary;
		CastList *ourCast = movieWithCast -> review.cast;
		if (ourCast == NULL) {
			movieWithCast -> review.cast = newMember;
		}
		else {
			while (ourCast -> next != NULL) {
				ourCast = ourCast -> next;
			}
			ourCast -> next = newMember;
		}
	}
}

float calculateEarnings(ReviewNode *movie) {
	float income = movie -> review.BO_total;
	double incomeD = (double)income;
	double salaries = 0;
	CastList *castMembers = movie -> review.cast;
	while (castMembers != NULL) {
		float salary = castMembers -> salary;
		double salaryD = (double)salary;
		salaries += salaryD;
		castMembers = castMembers -> next;
	}
	double earnings = incomeD - salaries;
	float earningsF = (float)earnings;
	return earningsF;

}

CastList *findCast(char name[MAX_STR_LEN], CastList *head)
{

	CastList *p=NULL;
	p=head;
	while (p!=NULL) {
		if (  ( strcmp( p -> name, name ) ) == 0 ) {
			return p;
		}
		p = p -> next;
	}
	return NULL;  
}

CastList *insertCast( char name[MAX_STR_LEN], CastList *head) {
		
			if ( head == NULL) {
				CastList *new_head = newCast();
				strcpy( new_head -> name, name);
				head = new_head;
			}
			else {

				CastList *found = findCast(name, head);
				if (found  == NULL ) {
 					CastList *actor_to_add = newCast();
	
					strcpy(actor_to_add->name, name);
					actor_to_add->next=head;
			
					head = actor_to_add;
				}
			}
	return head; 
}

float calculateAvg(char name[MAX_STR_LEN], ReviewNode *head) {
		
		double total = 0.0;
		double num = 0.0;
		ReviewNode *p = NULL;
		p = head;
		while (p != NULL) {
			if ( findCast(name, p -> review.cast) != NULL) {
				float earnings = calculateEarnings(p);
				double earningsD = (double)earnings;
				total += earningsD;
				num += 1;
			}
			p = p -> next;
		}
		double average = total/num;
		float averageF = (float)average;
		return averageF;
}

void whosTheStar(ReviewNode *head)
{
  /*
   *  This function goes through the movie database and determines who is
   * the cast members whose movies have the greatest average earnings.
   *
   * Earnings are defined as the box office total minus the salaries of all
   * cast members involved with the movie.
   *
   *  You're free to implement this function however you like, use any of
   * the code you wrote for other parts of the assignment, and write any
   * helper functions that you need. But:
   *
   *  You can not import extra libraries (no additional #include), and
   * all the implementation here should be your own. 
   *
   *  The function simply prints out:
   *
   *  Name of cast member
   *  Average movie earnings (as a floating point number)
   *
   *  For the cast member whose movies make the greatest average earnings
   */

    /***************************************************************************/
    /**********  TO DO: Complete this function *********************************/
    /***************************************************************************/      


	
	ReviewNode *p = NULL;
	p = head;
	
	if (p == NULL) {
		printf("No Movies in Database Yet.\n");
	}

	else {
		CastList *allCasts =NULL;
		while (p != NULL) {
			CastList *c = NULL;
			c = p -> review.cast;
			while(c != NULL) {
				allCasts = insertCast(c -> name, allCasts);      // insert c to allCast
				c = c -> next;
			}
			p = p-> next;
		}

		if ( allCasts == NULL) {
			printf("No Cast In All Movies\n");
		}

		else {
			CastList *ourStar = newCast();
			CastList *q = allCasts;
			float starAvg =  calculateAvg(allCasts -> name, head);
			strcpy(ourStar -> name, allCasts -> name);
			q = q -> next;
			while (q != NULL) {
				float avg = calculateAvg(q -> name, head);
				if ( avg > starAvg) {
					starAvg = avg;
					strcpy(ourStar -> name, q -> name);
				}
				q = q -> next;
			}
				
			printf("%s\n", ourStar -> name);
			printf("%f\n", starAvg);
		}

	}
}

void printNames(ReviewNode *movie)
{

 CastList *p;

 if (movie == NULL || movie -> review.cast == NULL) return ;


  p = movie -> review.cast;

  printf("The cast for this movie are:\n");
 while (p != NULL)
  {
   printf ("Cast member: %s, salary: %f\n", p->name,p->salary);
   p = p->next;
   }
   }
