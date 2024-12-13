-- ~~rework message entity as posts~~
-- ~~create comments entity~~
-- ~~create likes entity~~
-- ~~create follows entity~~

queries:
    find posts liked by user
    find all likes for a post
    find followers of user
    find users being followed by user
    find comments on post

    dont actually have to do any of this stuff because it is now defined on the entities. 
    simply select the entity and you can view it's relational data via getter methods
    however, it is still probably better to abstract this to the service layer to keep rest controller clean.
    

    accounts-follows
    one to many and many to one? *
    or many to many?
