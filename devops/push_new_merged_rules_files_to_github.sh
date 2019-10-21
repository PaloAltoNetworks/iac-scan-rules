#!/bin/sh -x
git branch
git checkout $1
git status
needToPush=$(git status | grep -c "Changes not staged for commit:")
echo "needToPush=$needToPush"
echo "is there a need to push new change?: determine based on if any changes detected in branch"
echo "0= no changes and branch upto date. >0 : changes to be commited and pushed"
if [ $needToPush -gt 0 ]; then
    echo "**pushing the changes to github**"
    git add src/main/resources/content/cft.json
    git add src/main/resources/content/k8s.json
    git add src/main/resources/content/tf.json
    git status
    git commit -m "merged rules files"
    git status
    git push
else
    echo "**no need to push to github**"
fi